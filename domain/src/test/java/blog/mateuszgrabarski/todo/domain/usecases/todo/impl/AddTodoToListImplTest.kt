package blog.mateuszgrabarski.todo.domain.usecases.todo.impl

import blog.mateuszgrabarski.todo.domain.data.factories.TodoFactory
import blog.mateuszgrabarski.todo.domain.fakes.FakeToDoListRepository
import blog.mateuszgrabarski.todo.domain.fakes.FakeTodoRepository
import blog.mateuszgrabarski.todo.domain.fakes.data.anyTodoList
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.Todo
import blog.mateuszgrabarski.todo.domain.usecases.todo.AddTodoToList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AddTodoToListImplTest {

    private val todoFactory = TodoFactory()
    private val listRepository = FakeToDoListRepository()
    private val todoRepository = FakeTodoRepository()
    private val sut = AddTodoToListImpl(todoFactory, listRepository, todoRepository)

    @Test
    internal fun `Creates todo in list`() = runBlocking {
        listRepository.saveList(anyTodoList(ANY_LIST_ID))

        sut.execute(
            Arguments(
                description = VALID_DESCRIPTION,
                listId = ANY_LIST_ID
            )
        ).collect {
            val result = it as Success<Todo>
            assertEquals(todoRepository.getById(result.data.id), result.data)
        }
    }

    companion object {
        private val ANY_LIST_ID = Id.randomUUID()
        private const val VALID_DESCRIPTION = "description"
    }
}