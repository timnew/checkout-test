package au.com.seek.checkout

abstract class CheckoutVisitor {
    open fun visit(checkout: Checkout) {
        checkout.items.forEach(this::visit)
    }

    protected open fun visit(item: Checkout.Item) {
        if (isMatch(item)) {
            applyRule(item)
        }
    }

    protected abstract fun applyRule(item: Checkout.Item)
    protected abstract fun isMatch(item: Checkout.Item): Boolean
}