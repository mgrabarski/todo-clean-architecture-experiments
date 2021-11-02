package blog.mateuszgrabarski.todo.domain.models

data class Todo(
    val id: Id,
    val description: String,
    val createDate: DateTime,
    val modificationDate: DateTime?,
    val todoListId: Id
)