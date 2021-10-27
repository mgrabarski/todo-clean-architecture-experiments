package blog.mateuszgrabarski.todo.domain.fakes

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository

class FakeToDoListRepository : TodoListRepository {

    private val lists = mutableMapOf<Id, TodoList>()

    override suspend fun saveList(newList: TodoList) {
        lists[newList.id] = newList
    }
}