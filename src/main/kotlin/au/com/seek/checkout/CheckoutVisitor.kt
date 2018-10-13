package au.com.seek.checkout

abstract class CheckoutVisitor {
    open fun visit(checkout: Checkout) {
        checkout.items.forEach(this::visit)
    }

    open fun visit(item: Checkout.Item) {
        if (isMatch(item)) {
            applyRule(item)
        }
    }

    abstract fun applyRule(item: Checkout.Item)
    abstract fun isMatch(item: Checkout.Item): Boolean
}