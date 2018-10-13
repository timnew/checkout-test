package au.com.seek.checkout

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GivenExamples {
    private val pricingRules: List<PricingRule> = listOf()

    private val classic = Product("Classic Ad", 269.99)
    private val standout = Product("Stand out Ad", 322.99)
    private val premium = Product("Premium Ad", 394.99)


    @Test
    fun `example for default`() {
        val checkout = Checkout(pricingRules, "default")

        checkout.add(classic)
        checkout.add(standout)
        checkout.add(premium)

        Assertions.assertThat(checkout.total).isEqualTo(987.97)
    }

    @Test
    fun `example for SecondBite`() {
        val checkout = Checkout(pricingRules, "SecondBite")

        checkout.add(classic)
        checkout.add(classic)
        checkout.add(classic)
        checkout.add(premium)

        Assertions.assertThat(checkout.total).isEqualTo(934.97)
    }

    @Test
    fun `example for Axil Coffee Roasters`() {
        val checkout = Checkout(pricingRules, "Axil Coffee Roasters")

        checkout.add(standout)
        checkout.add(standout)
        checkout.add(standout)
        checkout.add(premium)

        assertThat(checkout.total).isEqualTo(1294.96)
    }
}