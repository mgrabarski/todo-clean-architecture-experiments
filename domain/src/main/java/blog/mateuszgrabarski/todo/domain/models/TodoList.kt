package blog.mateuszgrabarski.todo.domain.models

data class TodoList(
    val id: Id,
    var name: String,
    var description: String,
    val createDate: DateTime,
    var modificationDateTime: DateTime?,
    private val _todos: MutableList<Todo> = mutableListOf()
) {

    val todos: List<Todo>
        get() = _todos.toList()

    fun addTodo(todo: Todo) {
        _todos.add(todo)
    }
}