package blog.mateuszgrabarski.todo.domain.data.validators

class TodoListNameValidator {

    fun isValid(name: String): Boolean = name.isNotEmpty()
}