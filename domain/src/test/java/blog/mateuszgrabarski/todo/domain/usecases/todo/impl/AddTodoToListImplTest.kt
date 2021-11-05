package blog.mateuszgrabarski.todo.domain.usecases.todo.impl

import blog.mateuszgrabarski.todo.domain.data.factories.TodoFactory
import blog.mateuszgrabarski.todo.domain.data.validators.TodoDescriptionValidator
import blog.mateuszgrabarski.todo.domain.fakes.FakeToDoListRepository
import blog.mateuszgrabarski.todo.domain.fakes.FakeTodoRepository
import blog.mateuszgrabarski.todo.domain.fakes.data.anyTodoList
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.Todo
import blog.mateuszgrabarski.todo.domain.usecases.todo.AddTodoToList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.todo.AddTodoToList.Companion.ERROR_EMPTY_DESCRIPTION
import blog.mateuszgrabarski.todo.domain.usecases.todo.AddTodoToList.Companion.ERROR_LIST_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AddTodoToListImplTest {

    private lateinit var nameValidator: TodoDescriptionValidator
    private lateinit var todoFactory: TodoFactory
    private lateinit var listRepository: FakeToDoListRepository
    private lateinit var todoRepository: FakeTodoRepository
    private lateinit var sut: AddTodoToListImpl

    @BeforeEach
    internal fun setUp() {
        nameValidator = TodoDescriptionValidator()
        todoFactory = TodoFactory()
        listRepository = FakeToDoListRepository()
        todoRepository = FakeTodoRepository()
        sut = AddTodoToListImpl(nameValidator, todoFactory, listRepository, todoRepository)
    }

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

    @Test
    internal fun `Failure when todo list not found`() = runBlocking {
        sut.execute(
            Arguments(
                description = VALID_DESCRIPTION,
                listId = ANY_LIST_ID
            )
        ).collect {
            val result = it as Failure
            assertEquals(ERROR_LIST_NOT_FOUND, result.message)
        }
    }

    @Test
    internal fun `Failure when todo description is not valid`() = runBlocking {
        sut.execute(
            Arguments(
                description = NOT_VALID_DESCRIPTION,
                listId = ANY_LIST_ID
            )
        ).collect {
            val result = it as Failure
            assertEquals(ERROR_EMPTY_DESCRIPTION, result.message)
        }
    }

    companion object {
        private val ANY_LIST_ID = Id.randomUUID()
        private const val VALID_DESCRIPTION = "description"
        private const val NOT_VALID_DESCRIPTION = ""
    }
}