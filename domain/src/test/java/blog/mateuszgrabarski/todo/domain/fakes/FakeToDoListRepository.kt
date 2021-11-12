package blog.mateuszgrabarski.todo.domain.fakes

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.repositories.Success
import blog.mateuszgrabarski.todo.domain.repositories.TodoListRepository

class FakeToDoListRepository : TodoListRepository {

    private val lists = mutableMapOf<Id, TodoList>()

    override suspend fun saveList(newList: TodoList): Success {
        lists[newList.id] = newList
        return true
    }

    override suspend fun getById(id: Id): TodoList? = lists[id]

    override suspend fun update(list: TodoList): Success {
        lists[list.id] = list
        return true
    }

    override suspend fun delete(id: Id): Success {
        lists.remove(id)
        return true
    }

    override suspend fun getAllLists(): List<TodoList> = lists.values.toList()
}