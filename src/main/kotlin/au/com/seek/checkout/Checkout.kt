package au.com.seek.checkout

class Checkout(
        val rules: List<PricingRule>,
        val items: MutableList<Item> = mutableListOf()
) {
    fun total(): Double = items.sumByDouble { it.finalPrice }

    fun add(product: Product) {
        items.add(Item(product))
    }

    data class Item(
            val product: Product
    ) {
        private var adjustedPrice: Double? = null

        val finalPrice: Double = adjustedPrice ?: product.price
    }
}
