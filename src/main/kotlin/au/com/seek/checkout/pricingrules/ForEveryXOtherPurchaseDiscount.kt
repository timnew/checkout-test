package au.com.seek.checkout.pricingrules

import au.com.seek.checkout.Checkout
import au.com.seek.checkout.PricingRule
import au.com.seek.checkout.ItemBasedVisitor

class ForEveryXOtherPurchaseDiscount(
        val productName: String,
        val x: Int,
        val discountedPrice: Double,
        override val name: String = "Every $x other purchase to get 1 $productName at $discountedPrice",
        override val priority: Int = PricingRule.DEFAULT_PRIORITY
) : PricingRule {
    override fun createVisitor(): Visitor = Visitor()

    inner class Visitor : ItemBasedVisitor() {
        private var remaining = 0

        override fun visit(checkout: Checkout) {
            calcRemaining(checkout)

            super.visit(checkout)
        }

        private fun calcRemaining(checkout: Checkout) {
            remaining = checkout.items
                    .filter { it.productName != productName }
                    .size / x
        }

        override fun applyRule(item: Checkout.Item) {
            item.addTags(name)
            item.adjustPrice(discountedPrice)
            remaining--
        }

        override fun isMatch(item: Checkout.Item): Boolean =
                item.productName == productName && remaining > 0
    }
}