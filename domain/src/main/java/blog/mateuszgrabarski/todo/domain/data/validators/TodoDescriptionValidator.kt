package blog.mateuszgrabarski.todo.domain.data.validators

class TodoDescriptionValidator {

    fun validate(description: String) = description.isNotEmpty()
}