package au.com.seek.checkout.pricingrules

import au.com.seek.checkout.Checkout
import au.com.seek.checkout.Product
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class ForEveryXOtherPurchaseDiscountTest {
    private val fullPrice = 200.0
    private val discountedPrice = 0.0

    private val discountProduct = Product("Discount Product", fullPrice)
    private val product = Product("Product", fullPrice)

    private val rule = ForEveryXOtherPurchaseDiscount(
            discountProduct.name,
            3,
            0.0
    )

    lateinit var visitor: ForEveryXOtherPurchaseDiscount.Visitor

    lateinit var checkout: Checkout

    @Before
    fun setUp() {
        visitor = rule.createVisitor()

        checkout = Checkout()

        checkout.add(discountProduct)
    }

    @Test
    fun `should discount`() {
        checkout.add(product)
        checkout.add(product)
        checkout.add(product)

        visitor.visit(checkout)

        val discountedItem = checkout.items.first { it.product == discountProduct }

        assertThat(discountedItem.finalPrice).isEqualTo(discountedPrice)
    }

    @Test
    fun `should not discount`() {
        checkout.add(product)
        checkout.add(product)

        visitor.visit(checkout)

        val discountedItem = checkout.items.first { it.product == discountProduct }

        assertThat(discountedItem.finalPrice).isEqualTo(fullPrice)
    }

    @Test
    fun `should have only one discount`() {
        checkout.add(product)
        checkout.add(product)
        checkout.add(product)

        checkout.add(discountProduct)

        visitor.visit(checkout)

        val discountedItems = checkout.items.filter { it.product == discountProduct }

        assertThat(discountedItems).extracting("finalPrice").containsExactlyInAnyOrder(fullPrice, discountedPrice)
    }

    @Test
    fun `should have more discounts`() {
        checkout.add(product)
        checkout.add(product)
        checkout.add(product)

        checkout.add(discountProduct)

        checkout.add(product)
        checkout.add(product)
        checkout.add(product)

        visitor.visit(checkout)

        val discountedItems = checkout.items.filter { it.product == discountProduct }

        assertThat(discountedItems).extracting("finalPrice").containsExactlyInAnyOrder(discountedPrice, discountedPrice)
    }
}