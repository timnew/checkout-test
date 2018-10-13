package au.com.seek.checkout

import io.mockk.spyk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Ignore
import org.junit.Test

class CheckoutTest {
    private val customer = "customer"

    private val product = Product("Product", 100.0)

    @Test
    fun `should create checkout`() {
        val checkout = Checkout()

        assertThat(checkout.customer).isEqualTo("default")
        assertThat(checkout.items).isEmpty()
        assertThat(checkout.isSealed).isFalse()

        assertThat(checkout.total).isZero()
    }

    @Ignore("not sure why calculatedTotal invocation cannot be captured")
    @Test
    fun `should not calculate price twice`() {
        val checkout = spyk(Checkout())

        checkout.add(product)

        assertThat(checkout.total).isEqualTo(product.price)
        assertThat(checkout.total).isEqualTo(product.price)

        verify(exactly = 1) {
            checkout["calculateTotal"]()
        }
    }

    @Test
    fun `should seal checkout after calculate price`() {
        val checkout = Checkout()

        checkout.add(product)

        assertThat(checkout.total).isEqualTo(product.price)

        assertThat(checkout.isSealed).isTrue()
    }

    @Test
    fun `should throw sealed exception when update sealed checkout`() {
        val checkout = Checkout()

        checkout.add(product)

        assertThat(checkout.total).isEqualTo(product.price)

        assertThat(checkout.isSealed).isTrue()

        assertThatThrownBy { checkout.add(product) }
                .isInstanceOf(SealedCheckoutException::class.java)
                .hasMessage("Cannot update sealed checkout")

    }

    @Test
    fun `should add product as item`() {
        val checkout = Checkout(listOf(), customer = customer)

        checkout.add(product)

        val item = checkout.items.first()

        assertThat(item.product).isEqualTo(product)
        assertThat(item.finalPrice).isEqualTo(product.price)
        assertThat(item.customer).isEqualTo(checkout.customer)
    }

    @Test
    fun `should add product for different customer`() {
        val newCustomer = "new customer"

        val checkout = Checkout(listOf(), customer = customer)

        checkout.add(product, newCustomer)

        val item = checkout.items.first()

        assertThat(item.product).isEqualTo(product)
        assertThat(item.finalPrice).isEqualTo(product.price)
        assertThat(item.customer).isEqualTo(newCustomer)
    }
}