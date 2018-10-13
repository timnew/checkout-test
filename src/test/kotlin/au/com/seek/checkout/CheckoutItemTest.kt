package au.com.seek.checkout

import au.com.seek.checkout.Checkout.Item.Companion.TAG_FINAL
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class CheckoutItemTest {

    private val adjustedPrice = 1.0
    private val product = Product("Product", 100.0)
    private val customer = "Customer"

    lateinit var item: Checkout.Item

    @Before
    fun setUp() {
        item = Checkout.Item(product, customer)
    }

    @Test
    fun `has no tags`() {
        assertThat(item.tags).isEmpty()
    }

    @Test
    fun `is not final`() {
        assertThat(item.isFinal).isFalse()
    }

    @Test
    fun `can mark as final`() {
        item.markFinal()

        assertThat(item.isFinal).isTrue()
        assertThat(item.hasTag(TAG_FINAL)).isTrue()
    }

    @Test
    fun `can add tags`() {
        item.addTags("A", "B")

        assertThat(item.hasTag("A")).isTrue()
        assertThat(item.hasTag("B")).isTrue()
    }

    @Test
    fun `item should have customer`() {
        assertThat(item.customer).isEqualTo(customer)
    }

    @Test
    fun `item should have product name`() {
        assertThat(item.productName).isEqualTo(product.name)
    }

    @Test
    fun `item should have product price before adjusted`() {
        assertThat(item.finalPrice).isEqualTo(product.price)
    }

    @Test
    fun `item should adjust price`() {
        item.adjustPrice(adjustedPrice)
        assertThat(item.finalPrice).isEqualTo(adjustedPrice)
    }
}