package blog.mateuszgrabarski.todo.domain.usecases.todo.impl

import app.cash.turbine.test
import blog.mateuszgrabarski.todo.domain.data.factories.TodoFactory
import blog.mateuszgrabarski.todo.domain.data.validators.TodoDescriptionValidator
import blog.mateuszgrabarski.todo.domain.fakes.FakeToDoListRepository
import blog.mateuszgrabarski.todo.domain.fakes.FakeTodoRepository
import blog.mateuszgrabarski.todo.domain.fakes.data.anyTodoList
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.Todo
import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository
import blog.mateuszgrabarski.todo.domain.repositories.TodoRepository
import blog.mateuszgrabarski.todo.domain.usecases.todo.AddTodoToList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.todo.AddTodoToList.Companion.ERROR_EMPTY_DESCRIPTION
import blog.mateuszgrabarski.todo.domain.usecases.todo.AddTodoToList.Companion.ERROR_LIST_NOT_FOUND
import blog.mateuszgrabarski.todo.domain.usecases.todo.AddTodoToList.Companion.ERROR_UNKNOWN
import blog.mateuszgrabarski.todo.domain.usecases.utils.Failure
import blog.mateuszgrabarski.todo.domain.usecases.utils.Success
import io.mockk.coEvery
import io.mockk.mockk
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
        ).test {
            val result = awaitItem() as Success<Todo>
            assertEquals(todoRepository.getById(result.data.id), result.data)
            awaitComplete()
        }
    }

    @Test
    internal fun `Failure when todo list not found`() = runBlocking {
        sut.execute(
            Arguments(
                description = VALID_DESCRIPTION,
                listId = ANY_LIST_ID
            )
        ).test {
            val result = awaitItem() as Failure
            assertEquals(ERROR_LIST_NOT_FOUND, result.message)
            awaitComplete()
        }
    }

    @Test
    internal fun `Failure when todo description is not valid`() = runBlocking {
        sut.execute(
            Arguments(
                description = NOT_VALID_DESCRIPTION,
                listId = ANY_LIST_ID
            )
        ).test {
            val result = awaitItem() as Failure
            assertEquals(ERROR_EMPTY_DESCRIPTION, result.message)
            awaitComplete()
        }
    }

    @Test
    internal fun `Failures when repository return no success of creating todo`() = runBlocking {
        val listRepository = mockk<TodoListRepository>()
        coEvery { listRepository.getById(any()) } returns anyTodoList()

        val todoRepository = mockk<TodoRepository>()
        coEvery { todoRepository.saveTodo(any()) } returns false

        AddTodoToListImpl(nameValidator, todoFactory, listRepository, todoRepository).execute(
            Arguments(
                ANY_LIST_ID,
                VALID_DESCRIPTION
            )
        ).test {
            val result = awaitItem() as Failure
            assertEquals(ERROR_UNKNOWN, result.message)
            awaitComplete()
        }
    }

    companion object {
        private val ANY_LIST_ID = Id.randomUUID()
        private const val VALID_DESCRIPTION = "description"
        private const val NOT_VALID_DESCRIPTION = ""
    }
}