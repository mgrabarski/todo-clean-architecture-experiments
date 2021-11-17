package blog.mateuszgrabarski.todo.data.model.mappers.specific

import blog.mateuszgrabarski.todo.data.model.entities.TodoEntity
import blog.mateuszgrabarski.todo.data.model.mappers.DomainModelMapper
import blog.mateuszgrabarski.todo.domain.models.Todo

class TodoMapper : DomainModelMapper<TodoEntity, Todo> {

    override fun mapFromEntity(entity: TodoEntity) = Todo(
        id = entity.id,
        description = entity.description,
        createDate = entity.createDate,
        modificationDate = entity.modificationDate,
        todoListId = entity.todoListId
    )

    override fun mapToEntity(domainModel: Todo) = TodoEntity(
        id = domainModel.id,
        description = domainModel.description,
        createDate = domainModel.createDate,
        modificationDate = domainModel.modificationDate,
        todoListId = domainModel.todoListId
    )
}