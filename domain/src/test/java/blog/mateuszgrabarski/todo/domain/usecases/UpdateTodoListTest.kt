package blog.mateuszgrabarski.todo.domain.usecases

import blog.mateuszgrabarski.todo.domain.fakes.FakeToDoListRepository
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.usecases.UpdateTodoList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.UpdateTodoList.Companion.ERROR_LIST_NOT_FOUND
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class UpdateTodoListTest {

    private val repository = FakeToDoListRepository()
    private val sut = UpdateTodoList(repository)

    @Test
    internal fun `emits failure when list not found in repository`() = runBlocking {
        sut.execute(
            Arguments(
                listId = ANY_ID,
                newName = NEW_NAME,
                newDescription = NEW_DESCRIPTION
            )
        ).collect {
            val result = it as Failure
            assertEquals(ERROR_LIST_NOT_FOUND, result.message)
        }
    }

    @Test
    internal fun `Updates list`() = runBlocking {
        repository.saveList(anyTodoList())
        sut.execute(
            Arguments(
                listId = ANY_ID,
                newName = NEW_NAME,
                newDescription = NEW_DESCRIPTION
            )
        ).collect {
            val emittedResult = it is Success<TodoList>
            assertTrue(emittedResult)

            val result = repository.getById(ANY_ID)!!

            assertEquals(NEW_NAME, result.name)
            assertEquals(NEW_DESCRIPTION, result.description)
            assertNotNull(result.modificationDateTime)
        }
    }

    private fun anyTodoList() = TodoList(
        id = ANY_ID,
        name = "name",
        description = "",
        createDate = DateTime.now(),
        null
    )

    companion object {
        private val ANY_ID = Id.randomUUID()
        private const val NEW_NAME = "naw name"
        private const val NEW_DESCRIPTION = "new description"
    }
}