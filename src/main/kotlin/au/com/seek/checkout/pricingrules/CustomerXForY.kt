package au.com.seek.checkout.pricingrules

import au.com.seek.checkout.Checkout
import au.com.seek.checkout.SimpleBaseVisitor
import au.com.seek.checkout.PricingRule

class CustomerXForY(
        val customer: String,
        val x: Int,
        val y: Int,
        val productName: String,
        override val name: String = "$customer $x for $y $productName",
        override val priority: Int = PricingRule.DEFAULT_PRIORITY
) : PricingRule {
    override fun createVisitor(): SimpleBaseVisitor = Visitor()

    inner class Visitor : SimpleBaseVisitor() {
        private var count = 0

        override fun applyRule(item: Checkout.Item) {
            count++

            if (count > y) {
                item.adjustPrice(0.0)
            }

            if (count == x) {
                count = 0
            }
        }

        override fun isMatch(item: Checkout.Item): Boolean = item.customer == customer && item.productName == productName
    }
}