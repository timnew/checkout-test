package au.com.seek.checkout

abstract class ItemBasedVisitor : PricingRule.Visitor {
    override fun visit(checkout: Checkout) {
        checkout.items
                .filterNot(Checkout.Item::isFinal)
                .filter(this::isMatch)
                .forEach(this::applyRule)
    }

    abstract fun applyRule(item: Checkout.Item)
    abstract fun isMatch(item: Checkout.Item): Boolean
}
