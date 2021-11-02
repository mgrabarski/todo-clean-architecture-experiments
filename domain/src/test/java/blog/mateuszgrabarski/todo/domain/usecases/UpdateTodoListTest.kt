package blog.mateuszgrabarski.todo.domain.usecases

import blog.mateuszgrabarski.todo.domain.fakes.FakeToDoListRepository
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.usecases.UpdateTodoList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.UpdateTodoList.Companion.ERROR_LIST_NOT_FOUND
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UpdateTodoListTest {

    private val sut = UpdateTodoList(FakeToDoListRepository())

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

    companion object {
        private val ANY_ID = Id.randomUUID()
        private const val NEW_NAME = "naw name"
        private const val NEW_DESCRIPTION = "new description"
    }
}