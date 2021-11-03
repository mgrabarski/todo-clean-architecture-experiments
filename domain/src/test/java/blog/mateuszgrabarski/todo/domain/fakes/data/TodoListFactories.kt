package blog.mateuszgrabarski.todo.domain.fakes.data

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.TodoList
import org.joda.time.DateTime

fun anyTodoList(id: Id = Id.randomUUID()) = TodoList(
    id = id,
    name = "name",
    description = "description",
    createDate = DateTime.now(),
    null
)
