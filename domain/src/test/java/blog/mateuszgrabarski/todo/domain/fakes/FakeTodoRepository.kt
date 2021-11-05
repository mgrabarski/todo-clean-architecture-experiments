package blog.mateuszgrabarski.todo.domain.fakes

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.Todo
import blog.mateuszgrabarski.todo.domain.repositories.TodoRepository

class FakeTodoRepository : TodoRepository {

    private val todos = mutableMapOf<Id, Todo>()

    override suspend fun saveTodo(todo: Todo) {
        todos[todo.id] = todo
    }

    override suspend fun getById(id: Id): Todo? = todos[id]

    override suspend fun delete(todo: Todo) {
        todos.remove(todo.id)
    }
}