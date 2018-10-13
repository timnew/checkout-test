package au.com.seek.checkout

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