package blog.mateuszgrabarski.todo.domain.data.factories

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class TodoListFactoryTest {

    private val sut = TodoListFactory()

    @Test
    internal fun `Creates new todo list`() {
        val result = sut.create(SOME_NAME, SOME_DESCRIPTION)

        assertEquals(SOME_NAME, result.name)
        assertEquals(SOME_DESCRIPTION, result.description)
        assertNull(result.modificationDateTime)
    }

    companion object {
        private const val SOME_NAME = "name"
        private const val SOME_DESCRIPTION = ""
    }
}