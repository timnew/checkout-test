package au.com.seek.checkout

interface PricingRule {
    val name: String

    val priority: Int

    fun createVisitor(): Visitor

    companion object {
        const val HIGH_PRIORITY = 50
        const val DEFAULT_PRIORITY = 100
    }

    interface Visitor {
        fun visit(checkout: Checkout)
    }
}


