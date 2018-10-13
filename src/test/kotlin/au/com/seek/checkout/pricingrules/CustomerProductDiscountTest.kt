package au.com.seek.checkout.pricingrules

import au.com.seek.checkout.Checkout
import au.com.seek.checkout.ItemBasedVisitor
import au.com.seek.checkout.Product
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class CustomerProductDiscountTest {

    private val fullPrice = 200.0
    private val discountedPrice = 100.0

    private val discountedProduct = Product("Discountable Product", fullPrice)
    private val fullPriceProduct = Product("Fullprice Product", fullPrice)

    private val normalCustomer = "default"
    private val discountedCustomer = "discounted customer"

    private val pricingRule = CustomerProductDiscount(discountedCustomer, discountedProduct.name, discountedPrice)

    lateinit var visitor: ItemBasedVisitor

    @Before
    fun setUp() {
        visitor = pricingRule.createVisitor() as ItemBasedVisitor
    }

    @Test
    fun `it should not impact normal price calculation`() {
        val item = Checkout.Item(fullPriceProduct, normalCustomer)

        assertThat(visitor.isMatch(item)).isFalse()

        visitor.visit(item)

        assertThat(item.finalPrice).isEqualTo(fullPrice)
    }

    @Test
    fun `it should discount for privileged customer on certain product`() {
        val item = Checkout.Item(discountedProduct, discountedCustomer)

        assertThat(visitor.isMatch(item)).isTrue()

        visitor.visit(item)

        assertThat(item.finalPrice).isEqualTo(discountedPrice)
    }

    @Test
    fun `it should not discount for unprivileged customer`() {
        val item = Checkout.Item(discountedProduct, normalCustomer)

        assertThat(visitor.isMatch(item)).isFalse()

        visitor.visit(item)

        assertThat(item.finalPrice).isEqualTo(fullPrice)
    }

    @Test
    fun `it should not discount for normal product`() {
        val item = Checkout.Item(fullPriceProduct, discountedCustomer)

        assertThat(visitor.isMatch(item)).isFalse()

        visitor.visit(item)

        assertThat(item.finalPrice).isEqualTo(fullPrice)
    }
}