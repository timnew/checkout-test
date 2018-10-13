package au.com.seek.checkout

interface PricingRule {
    val name: String

    val priority: Int

    fun createVisitor(): CheckoutVisitor

    companion object {
        const val DEFAULT_PRIORITY = 100
    }
}


