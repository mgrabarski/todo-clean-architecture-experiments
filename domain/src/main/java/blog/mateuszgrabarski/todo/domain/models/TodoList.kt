package blog.mateuszgrabarski.todo.domain.models

data class TodoList(
    val id: Id,
    var name: String,
    var description: String,
    val createDate: DateTime,
    var modificationDateTime: DateTime?,
    private val _notCompletedTodos: MutableList<Todo> = mutableListOf(),
    private val _completedTodos: MutableList<Todo> = mutableListOf()
) {

    val notCompletedTodos: List<Todo>
        get() = _notCompletedTodos.toList()

    val completedTodos: List<Todo>
        get() = _completedTodos.toList()

    fun addNewTodo(todo: Todo) {
        _notCompletedTodos.add(todo)
    }
}