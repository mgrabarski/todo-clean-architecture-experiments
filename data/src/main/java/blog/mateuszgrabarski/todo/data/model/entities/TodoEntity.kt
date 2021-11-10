package blog.mateuszgrabarski.todo.data.model.entities

import blog.mateuszgrabarski.todo.domain.models.DateTime
import blog.mateuszgrabarski.todo.domain.models.Id

data class TodoEntity(
    val id: Id,
    val description: String,
    val createDate: DateTime,
    val modificationDate: DateTime?,
    val todoListId: Id
)