package blog.mateuszgrabarski.todo.domain.fakes.data

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.Todo
import org.joda.time.DateTime

fun anyTodo(id: Id = Id.randomUUID(), todoListId: Id = Id.randomUUID()) = Todo(
    id = id,
    description = "description",
    createDate = DateTime.now(),
    modificationDate = null,
    todoListId = todoListId
)