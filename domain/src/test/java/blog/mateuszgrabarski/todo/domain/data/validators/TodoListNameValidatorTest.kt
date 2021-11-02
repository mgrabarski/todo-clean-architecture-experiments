package blog.mateuszgrabarski.todo.domain.data.validators

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class TodoListNameValidatorTest {

    private val sut = TodoListNameValidator()

    @Test
    internal fun `Positive validation for valid name`() {
        assertTrue(sut.isValid(VALID_NAME))
    }

    @Test
    internal fun `Not positive validation for not valid name`() {
        assertFalse(sut.isValid(EMPTY_NAME))
    }

    companion object {
        private const val VALID_NAME = "name"
        private const val EMPTY_NAME = ""
    }
}