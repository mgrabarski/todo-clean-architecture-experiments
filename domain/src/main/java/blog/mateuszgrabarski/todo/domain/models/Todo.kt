package blog.mateuszgrabarski.todo.domain.models

data class Todo(
    val id: Id,
    var description: String,
    val createDate: DateTime,
    var modificationDate: DateTime?,
    val todoListId: Id
)