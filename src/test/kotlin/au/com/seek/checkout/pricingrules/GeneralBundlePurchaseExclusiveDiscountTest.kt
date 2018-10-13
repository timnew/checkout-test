package au.com.seek.checkout.pricingrules

import au.com.seek.checkout.Checkout
import au.com.seek.checkout.PricingRule
import au.com.seek.checkout.Product
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test


class GeneralBundlePurchaseExclusiveDiscountTest {
    val rule: GeneralBundlePurchaseExclusiveDiscount = GeneralBundlePurchaseExclusiveDiscount(5, .5)

    val fullPrice = 200.0
    val halfPrice = fullPrice * .5

    val product1 = Product("Product 1", fullPrice)
    val product2 = Product("Product 2", fullPrice)
    val product3 = Product("Product 3", fullPrice)

    lateinit var checkout: Checkout
    lateinit var visitor: GeneralBundlePurchaseExclusiveDiscount.Visitor

    lateinit var itemForProduct1: Checkout.Item
    lateinit var itemForProduct2: Checkout.Item
    lateinit var itemForProduct3: Checkout.Item

    @Before
    fun setUp() {
        checkout = Checkout(listOf(rule))

        (1..5).forEach { checkout.add(product1) }
        (1..10).forEach { checkout.add(product2) }
        (1..2).forEach { checkout.add(product3) }

        visitor = rule.createVisitor()

        visitor.visit(checkout)

        itemForProduct1 = checkout.items.first { it.product == product1 }
        itemForProduct2 = checkout.items.first { it.product == product2 }
        itemForProduct3 = checkout.items.first { it.product == product3 }

    }

    @Test
    fun `rule has high priority`() {
        assertThat(rule.priority).isEqualTo(PricingRule.HIGH_PRIORITY)
    }


    @Test
    fun `should alter matched price by ratio`() {
        assertThat(itemForProduct1.finalPrice).isEqualTo(halfPrice)
        assertThat(itemForProduct2.finalPrice).isEqualTo(halfPrice)
        assertThat(itemForProduct3.finalPrice).isEqualTo(fullPrice)
    }

    @Test
    fun `should mark entry final if applied`() {
        assertThat(itemForProduct1.isFinal).isTrue()
        assertThat(itemForProduct2.isFinal).isTrue()
        assertThat(itemForProduct3.isFinal).isFalse()
    }
}