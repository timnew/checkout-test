package au.com.seek.checkout.pricingrules

import au.com.seek.checkout.Checkout
import au.com.seek.checkout.PricingRule
import au.com.seek.checkout.ItemBasedVisitor

class GeneralBundlePurchaseExclusiveDiscount(
        val minimumVolume: Int,
        val discountRatio: Double,
        override val name: String = "Exclusive ${discountRatio * 100}% discount for bundle above $minimumVolume",
        override val priority: Int = PricingRule.HIGH_PRIORITY
) : PricingRule {
    override fun createVisitor(): Visitor = Visitor()

    inner class Visitor : PricingRule.Visitor {

        override fun visit(checkout: Checkout) {
            checkout.items
                    .groupBy { it.productName }
                    .filter { it.value.size >= minimumVolume }
                    .flatMap { it.value }
                    .filterNot(Checkout.Item::isFinal)
                    .forEach(this::applyRule)
        }

        private fun applyRule(item: Checkout.Item) {
            item.adjustPrice(item.product.price * discountRatio)
            item.markFinal()
            item.addTags(name)
        }
    }
}