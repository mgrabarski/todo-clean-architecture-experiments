package blog.mateuszgrabarski.todo.domain.models

import org.joda.time.DateTime

data class TodoList(
    val id: Id,
    var name: String,
    var description: String,
    val createDate: DateTime,
    var modificationDateTime: DateTime?
)