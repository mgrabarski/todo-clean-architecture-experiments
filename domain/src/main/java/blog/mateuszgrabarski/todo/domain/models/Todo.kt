package blog.mateuszgrabarski.todo.domain.models

import org.joda.time.DateTime

data class Todo(
    val id: Id,
    val description: String,
    val createDate: DateTime,
    val modificationDate: DateTime?,
    val todoListId: Id
)