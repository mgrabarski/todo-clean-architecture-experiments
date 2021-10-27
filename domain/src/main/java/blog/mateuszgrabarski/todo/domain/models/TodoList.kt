package blog.mateuszgrabarski.todo.domain.models

import org.joda.time.DateTime

data class TodoList(
    val id: Id,
    val name: String,
    val description: String,
    val createDate: DateTime,
    val modificationDateTime: DateTime?
)