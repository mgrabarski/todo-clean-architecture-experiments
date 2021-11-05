package blog.mateuszgrabarski.todo.domain.usecases.todo.impl

import blog.mateuszgrabarski.todo.domain.fakes.FakeToDoListRepository
import blog.mateuszgrabarski.todo.domain.fakes.FakeTodoRepository

internal class AddTodoToListImplTest {

    private val listRepository = FakeToDoListRepository()
    private val todoRepository = FakeTodoRepository()
    private val sut = AddTodoToListImpl(listRepository, todoRepository)


}