package blog.mateuszgrabarski.todo.domain.data.factories

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.models.TodoList
import org.joda.time.DateTime

class TodoListFactory {

    fun create(name: String, description: String) = TodoList(
        id = Id.randomUUID(),
        name = name,
        description = description,
        createDate = DateTime.now(),
        modificationDateTime = null
    )
}