package blog.mateuszgrabarski.todo.data.model.mappers

interface DomainModelMapper<Entity, DomainModel> {
    fun mapFromEntity(entity: Entity): DomainModel
    fun mapToEntity(domainModel: DomainModel): Entity
}