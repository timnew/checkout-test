package au.com.seek.checkout

import au.com.seek.checkout.pricingrules.CustomerProductDiscount
import au.com.seek.checkout.pricingrules.CustomerXForY
import au.com.seek.checkout.pricingrules.ForEveryXOtherPurchaseDiscount
import au.com.seek.checkout.pricingrules.GeneralBundlePurchaseExclusiveDiscount
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset
import org.junit.Test

class GivenExamples {
    private val classic = Product("Classic Ad", 269.99)
    private val standout = Product("Stand out Ad", 322.99)
    private val premium = Product("Premium Ad", 394.99)
    private val jora = Product("Jora Membership", 599.99)

    private val secondBite = "SecondBite"
    private val axilCoffeeRoassters = "Axil Coffee Roasters"
    private val myer = "MYER"

    private val pricingRules: List<PricingRule> = listOf(
            CustomerXForY(secondBite, 3, 2, classic.name),

            CustomerProductDiscount(axilCoffeeRoassters, standout.name, 299.99),

            CustomerXForY(myer, 5, 4, standout.name),
            CustomerProductDiscount(myer, premium.name, 389.99),

            GeneralBundlePurchaseExclusiveDiscount(10, .5),

            ForEveryXOtherPurchaseDiscount(jora.name, 10, 0.0)
    )

    private val maxAcceptablePriceOffset = Offset.offset(.01)

    @Test
    fun `example for default`() {
        val checkout = Checkout(pricingRules)

        checkout.add(classic)
        checkout.add(standout)
        checkout.add(premium)

        Assertions.assertThat(checkout.total).isEqualTo(987.97)
    }

    @Test
    fun `example for SecondBite`() {
        val checkout = Checkout(pricingRules, secondBite)

        checkout.add(classic)
        checkout.add(classic)
        checkout.add(classic)
        checkout.add(premium)

        Assertions.assertThat(checkout.total).isEqualTo(934.97)

        val lastItem = checkout.items.last { it.product == classic }

        assertThat(lastItem.finalPrice).isEqualTo(0.0)
    }

    @Test
    fun `example for Axil Coffee Roasters`() {
        val checkout = Checkout(pricingRules, axilCoffeeRoassters)

        checkout.add(standout)
        checkout.add(standout)
        checkout.add(standout)
        checkout.add(premium)

        assertThat(checkout.total).isEqualTo(1294.96)

        val allStandoutItems = checkout.items.filter { it.product == standout }

        assertThat(allStandoutItems).allSatisfy {
            assertThat(it.finalPrice).isEqualTo(299.99)
        }
    }


    @Test
    fun `example for bundle purchase`() {
        val checkout = Checkout(pricingRules, axilCoffeeRoassters)

        (1..10).forEach { checkout.add(standout) }
        checkout.add(premium)

        Assertions.assertThat(checkout.total).isCloseTo(2009.94, maxAcceptablePriceOffset)

        val allStandoutItems = checkout.items.filter { it.product == standout }

        assertThat(allStandoutItems).allSatisfy {
            assertThat(it.finalPrice).isEqualTo(161.495)
        }
    }

    @Test
    fun `example for Jora membership discount`() {
        val checkout = Checkout(pricingRules)

        checkout.add(jora)

        checkout.add(classic)
        checkout.add(standout)
        checkout.add(premium)

        checkout.add(classic)
        checkout.add(standout)
        checkout.add(premium)

        checkout.add(classic)
        checkout.add(standout)
        checkout.add(premium)

        checkout.add(premium)

        Assertions.assertThat(checkout.total).isCloseTo(3358.9, maxAcceptablePriceOffset)

        val joraItem = checkout.items.first { it.product == jora }

        assertThat(joraItem.finalPrice).isEqualTo(0.0)
    }
}