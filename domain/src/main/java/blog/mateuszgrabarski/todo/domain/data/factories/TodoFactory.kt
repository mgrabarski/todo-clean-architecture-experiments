package blog.mateuszgrabarski.todo.domain.data.factories

import blog.mateuszgrabarski.todo.domain.models.DateTime
import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.Todo

class TodoFactory {

    fun create(description: String, todoListId: Id) = Todo(
        id = Id.randomUUID(),
        description = description,
        createDate = DateTime.now(),
        modificationDate = null,
        todoListId = todoListId
    )
}