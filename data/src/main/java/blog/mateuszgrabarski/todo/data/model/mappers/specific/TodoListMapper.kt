package blog.mateuszgrabarski.todo.data.model.mappers.specific

import blog.mateuszgrabarski.todo.data.model.entities.TodoListEntity
import blog.mateuszgrabarski.todo.data.model.mappers.DomainModelMapper
import blog.mateuszgrabarski.todo.domain.models.TodoList

class TodoListMapper : DomainModelMapper<TodoListEntity, TodoList> {

    override fun mapFromEntity(entity: TodoListEntity): TodoList =
        TodoList(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            createDate = entity.createDate,
            modificationDateTime = entity.modificationDateTime
        )

    override fun mapToEntity(domainModel: TodoList): TodoListEntity =
        TodoListEntity(
            id = domainModel.id,
            name = domainModel.name,
            description = domainModel.description,
            createDate = domainModel.createDate,
            modificationDateTime = domainModel.modificationDateTime
        )
}