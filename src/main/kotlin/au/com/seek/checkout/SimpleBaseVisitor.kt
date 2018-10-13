package au.com.seek.checkout

abstract class SimpleBaseVisitor : PricingRule.Visitor {
    override fun visit(checkout: Checkout) {
        checkout.items
                .filterNot(Checkout.Item::isFinal)
                .forEach(this::visit)
    }

    open fun visit(item: Checkout.Item) {
        if (isMatch(item)) {
            applyRule(item)
        }
    }

    abstract fun applyRule(item: Checkout.Item)
    abstract fun isMatch(item: Checkout.Item): Boolean
}