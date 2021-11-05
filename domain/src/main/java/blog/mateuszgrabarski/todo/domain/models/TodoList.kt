package blog.mateuszgrabarski.todo.domain.models

data class TodoList(
    val id: Id,
    var name: String,
    var description: String,
    val createDate: DateTime,
    var modificationDateTime: DateTime?,
    private val todos: MutableList<Todo> = mutableListOf()
) {

    fun addTodo(todo: Todo) {
        todos.add(todo)
    }
}