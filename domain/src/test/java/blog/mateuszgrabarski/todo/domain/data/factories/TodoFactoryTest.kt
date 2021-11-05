package blog.mateuszgrabarski.todo.domain.data.factories

import blog.mateuszgrabarski.todo.domain.models.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TodoFactoryTest {

    private val sut = TodoFactory()

    @Test
    internal fun `Creates new todo`() {
        val description = "description"
        val listId = Id.randomUUID()

        val result = sut.create(description, listId)

        assertEquals(description, result.description)
        assertEquals(listId, result.todoListId)
    }
}