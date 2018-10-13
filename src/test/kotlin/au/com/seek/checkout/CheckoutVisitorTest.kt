package au.com.seek.checkout

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.verifySequence
import org.junit.Before
import org.junit.Test

class CheckoutVisitorTest {

    @SpyK(recordPrivateCalls = true)
    var checkout: Checkout = Checkout()

    @RelaxedMockK
    lateinit var item1: Checkout.Item

    @RelaxedMockK
    lateinit var item2: Checkout.Item

    @RelaxedMockK
    lateinit var item3: Checkout.Item

    @RelaxedMockK
    lateinit var visitor: CheckoutVisitor


    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every { visitor.visit(any<Checkout>()) } answers { callOriginal() }
        every { visitor.visit(any<Checkout.Item>()) } answers { callOriginal() }

        every { checkout.items } returns listOf(item1, item2, item3)

        every { visitor["isMatch"](eq(item1)) } returns true
        every { visitor["isMatch"](eq(item2)) } returns true
    }

    @Test
    fun `it should run visit all items`() {
        visitor.visit(checkout)

        verifySequence {
            visitor.visit(eq(checkout))

            visitor.visit(eq(item1))
            visitor.isMatch(eq(item1))
            visitor.applyRule(eq(item1))

            visitor.visit(eq(item2))
            visitor.isMatch(eq(item2))
            visitor.applyRule(eq(item2))

            visitor.visit(eq(item3))
            visitor.isMatch(eq(item3))
        }
    }
}