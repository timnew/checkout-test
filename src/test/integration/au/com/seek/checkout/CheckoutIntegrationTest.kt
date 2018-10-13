package au.com.seek.checkout

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class CheckoutIntegrationTest {
    lateinit var pricingRules: List<PricingRule>

    private val classic = Product("Classic Ad", 269.99)
    private val standout = Product("Stand out Ad", 322.99)
    private val premium = Product("Premium Ad", 394.99)

    @Before
    fun setUp() {
        pricingRules = listOf()
    }

    @Test
    fun `should handle empty list`() {
        val checkout = Checkout(pricingRules)

        assertThat(checkout.total).isEqualTo(0.0)
    }

    @Test
    fun `should calculate 1 product prices`() {
        val checkout = Checkout(pricingRules)

        checkout.add(classic)

        assertThat(checkout.total).isEqualTo(269.99)
    }

    @Test
    fun `should calculate all product prices`() {
        val checkout = Checkout(pricingRules)

        checkout.add(classic)
        checkout.add(standout)
        checkout.add(premium)

        assertThat(checkout.total).isEqualTo(987.97)
    }


}