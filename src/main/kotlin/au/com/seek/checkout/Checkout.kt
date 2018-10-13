package au.com.seek.checkout

class Checkout(
        private val rules: List<PricingRule> = listOf(),
        private val customer: String = "default",
        val items: MutableList<Item> = mutableListOf()
) {
    val total: Double by lazy { calculateTotal() }

    private fun calculateTotal(): Double {
        applyRules()

        return items.sumByDouble { it.finalPrice }
    }

    private fun applyRules() {
        rules.forEach { it.createVisitor().visit(this) }
    }

    fun add(product: Product, productCustomer: String? = null) {
        items.add(Item(product, productCustomer ?: customer))
    }

    data class Item(
            val product: Product,
            val customer: String
    ) {
        val productName: String = product.name

        private var adjustedPrice: Double? = null

        fun adjustPrice(price: Double) {
            adjustedPrice = price
        }

        val finalPrice: Double = adjustedPrice ?: product.price
    }
}
