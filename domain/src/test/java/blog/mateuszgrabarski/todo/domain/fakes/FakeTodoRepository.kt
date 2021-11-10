package blog.mateuszgrabarski.todo.domain.fakes

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.Todo
import blog.mateuszgrabarski.todo.domain.repositories.TodoRepository

class FakeTodoRepository : TodoRepository {

    private val notCompleted = mutableMapOf<Id, Todo>()
    private val completed = mutableMapOf<Id, Todo>()

    override suspend fun saveTodo(todo: Todo) {
        notCompleted[todo.id] = todo
    }

    override suspend fun getById(id: Id): Todo? = notCompleted[id] ?: completed[id]

    override suspend fun delete(todo: Todo) {
        completed.remove(todo.id)
        notCompleted.remove(todo.id)
    }

    override suspend fun markAsCompleted(todo: Todo) {
        notCompleted.remove(todo.id)
        completed[todo.id] = todo
    }
}