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

    inner class Visitor : PricingRule.Visitor {
        override fun visit(checkout: Checkout) {
            val qualifiedCount = checkout.items
                    .filter { it.productName != productName }
                    .size / x

            checkout.items
                    .filter { it.productName == productName }
                    .filterNot(Checkout.Item::isFinal)
                    .take(qualifiedCount)
                    .forEach(this::applyRule)
        }

        private fun applyRule(item: Checkout.Item) {
            item.addTags(name)
            item.adjustPrice(discountedPrice)
        }
    }
}