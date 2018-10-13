package au.com.seek.checkout.pricingrules

import au.com.seek.checkout.Checkout
import au.com.seek.checkout.SimpleBaseVisitor
import au.com.seek.checkout.Product
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class CustomerXForYTest {
    private val discountCustomer = "discount customer"
    private val normalCustomer = "default"

    private val fullPrice = 200.0
    private val freePrice = 0.0

    private val discountProduct = Product("discount product", fullPrice)
    private val fullPriceProduct = Product("full price product", fullPrice)

    private val x = 4
    private val y = 2

    private val pricingRule = CustomerXForY(discountCustomer, x, y, discountProduct.name)

    private lateinit var visitor: SimpleBaseVisitor

    @Before
    fun setUp() {
        visitor = pricingRule.createVisitor()
    }

    @Test
    fun `should match`() {
        val item = Checkout.Item(discountProduct, discountCustomer)

        assertThat(visitor.isMatch(item)).isTrue()
    }

    @Test
    fun `should not match if product mismatched`() {
        val item = Checkout.Item(fullPriceProduct, discountCustomer)

        assertThat(visitor.isMatch(item)).isFalse()
    }

    @Test
    fun `should not match if customer mismatched`() {
        val item = Checkout.Item(discountProduct, normalCustomer)

        assertThat(visitor.isMatch(item)).isFalse()
    }

    @Test
    fun `should set 3rd and 4th item free`() {
        val checkout = Checkout(customer = discountCustomer)

        (1..5).forEach { checkout.add(discountProduct) }

        visitor.visit(checkout)


        val allPrices = checkout.items.map(Checkout.Item::finalPrice)

        assertThat(allPrices).containsExactly(fullPrice, fullPrice, freePrice, freePrice, fullPrice)
    }

    @Test
    fun `can apply multiple times`() {
        visitor = CustomerXForY(discountCustomer, 2, 1, discountProduct.name).createVisitor()

        val checkout = Checkout(customer = discountCustomer)

        (1..5).forEach { checkout.add(discountProduct) }

        visitor.visit(checkout)

        val allPrices = checkout.items.map(Checkout.Item::finalPrice)

        assertThat(allPrices).containsExactly(fullPrice, freePrice, fullPrice, freePrice, fullPrice)
    }
}