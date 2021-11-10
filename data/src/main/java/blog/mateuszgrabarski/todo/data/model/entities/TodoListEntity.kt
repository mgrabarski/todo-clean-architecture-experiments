package blog.mateuszgrabarski.todo.data.model.entities

import blog.mateuszgrabarski.todo.domain.models.DateTime
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.Todo

data class TodoListEntity(
    val id: Id,
    val name: String,
    val description: String,
    val createDate: DateTime,
    val modificationDateTime: DateTime?,
    private val notCompletedTodos: List<Todo> = emptyList(),
    private val completedTodos: List<Todo> = emptyList()
)