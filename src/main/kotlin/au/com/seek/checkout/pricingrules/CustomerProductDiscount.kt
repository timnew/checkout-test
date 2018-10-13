package au.com.seek.checkout.pricingrules

import au.com.seek.checkout.Checkout
import au.com.seek.checkout.CheckoutVisitor
import au.com.seek.checkout.PricingRule

class CustomerProductDiscount(
        val customer: String,
        val productName: String,
        val discountPrice: Double,
        override val name: String = "$customer $productName for discount price $discountPrice",
        override val priority: Int = PricingRule.DEFAULT_PRIORITY) : PricingRule {

    override fun createVisitor(): CheckoutVisitor = object : CheckoutVisitor() {
        override fun applyRule(item: Checkout.Item) {
            item.adjustPrice(discountPrice)
        }

        override fun isMatch(item: Checkout.Item): Boolean = item.customer == customer && item.productName == productName
    }
}