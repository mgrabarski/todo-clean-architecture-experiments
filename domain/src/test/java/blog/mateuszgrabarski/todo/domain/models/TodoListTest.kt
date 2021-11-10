package blog.mateuszgrabarski.todo.domain.models

import blog.mateuszgrabarski.todo.domain.fakes.data.anyTodo
import blog.mateuszgrabarski.todo.domain.fakes.data.anyTodoList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TodoListTest {

    @Test
    internal fun `Created list should have empty todos`() {
        val list = anyTodoList()

        assertEquals(0, list.notCompletedTodos.size)
        assertEquals(0, list.completedTodos.size)
    }

    @Test
    internal fun `Adds new todo to not completed list`() {
        val list = anyTodoList()
        list.addNewTodo(anyTodo())

        assertEquals(1, list.notCompletedTodos.size)
        assertEquals(0, list.completedTodos.size)
    }
}