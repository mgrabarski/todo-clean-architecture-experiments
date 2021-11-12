package blog.mateuszgrabarski.todo.domain.fakes

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.Todo
import blog.mateuszgrabarski.todo.domain.repositories.Success
import blog.mateuszgrabarski.todo.domain.repositories.TodoRepository

class FakeTodoRepository : TodoRepository {

    private val notCompleted = mutableMapOf<Id, Todo>()
    private val completed = mutableMapOf<Id, Todo>()

    override suspend fun saveTodo(todo: Todo): Success {
        notCompleted[todo.id] = todo
        return true
    }

    override suspend fun getById(id: Id): Todo? = notCompleted[id] ?: completed[id]

    override suspend fun delete(todo: Todo): Success {
        completed.remove(todo.id)
        notCompleted.remove(todo.id)
        return true
    }

    override suspend fun markAsCompleted(todo: Todo): Success {
        notCompleted.remove(todo.id)
        completed[todo.id] = todo
        return true
    }

    override suspend fun isCompleted(id: Id): Boolean = completed.containsKey(id)

    override suspend fun markAsNotCompleted(todo: Todo): Success {
        completed.remove(todo.id)
        notCompleted[todo.id] = todo
        return true
    }

    override suspend fun update(todo: Todo) {
        if (notCompleted.containsKey(todo.id)) {
            notCompleted[todo.id] = todo
        } else if (completed.containsKey(todo.id)) {
            completed[todo.id] = todo
        }
    }

    fun reset() {
        notCompleted.clear()
        completed.clear()
    }
}