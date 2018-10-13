package au.com.seek.checkout

class Checkout(
        pricingRules: List<PricingRule> = DEFAULT_PRICING_RULES,
        val customer: String = DEFAULT_CUSTOMER
) {
    val rules: List<PricingRule> = pricingRules.sortedBy(PricingRule::priority)

    val items: List<Item>
        get() = mutableItems

    private val mutableItems: MutableList<Item> = mutableListOf()

    var isSealed: Boolean = false
        private set(value) {
            field = value
        }


    val total: Double by lazy { calculateTotal() }

    private fun calculateTotal(): Double {
        isSealed = true

        rules.forEach { it.createVisitor().visit(this) }

        return items.sumByDouble { it.finalPrice }
    }

    fun add(product: Product, productCustomer: String? = null) {
        if (isSealed) throw SealedCheckoutException()
        mutableItems.add(Item(product, productCustomer ?: customer))
    }

    companion object {
        val DEFAULT_PRICING_RULES = emptyList<PricingRule>()
        const val DEFAULT_CUSTOMER = "default"
    }

    data class Item(
            val product: Product,
            val customer: String
    ) {
        val productName: String = product.name

        private var adjustedPrice: Double? = null

        val tags: Set<String>
            get() = mutableTags

        private val mutableTags: MutableSet<String> = mutableSetOf<String>()

        fun addTags(vararg newTags: String) =
                mutableTags.addAll(newTags)

        fun markFinal() = addTags(TAG_FINAL)

        fun hasTag(tag: String): Boolean = tags.contains(tag)

        val isFinal: Boolean
            get() = hasTag(TAG_FINAL)

        fun adjustPrice(price: Double) {
            adjustedPrice = price
        }

        val finalPrice: Double
            get() = adjustedPrice ?: product.price

        companion object {
            const val TAG_FINAL: String = "Final"
        }
    }
}
