package blog.mateuszgrabarski.todo.domain.data.validators

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class TodoDescriptionValidatorTest {

    private val sut = TodoDescriptionValidator()

    @Test
    internal fun `Positive validation`() {
        assertTrue(sut.validate("description"))
    }

    @Test
    internal fun `Negative validation for not valid description`() {
        assertFalse(sut.validate(""))
    }
}