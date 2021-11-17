package blog.mateuszgrabarski.todo.data.store.repositories.fakes

import blog.mateuszgrabarski.todo.data.model.entities.TodoEntity
import blog.mateuszgrabarski.todo.data.store.network.TodoRemoteDataSource
import blog.mateuszgrabarski.todo.domain.models.Id

class FakeTodoRemoteDataSource : TodoRemoteDataSource {

    private val notCompleted = mutableMapOf<Id, TodoEntity>()
    private val completed = mutableMapOf<Id, TodoEntity>()

    override suspend fun insertToNotCompleted(entity: TodoEntity) {
        notCompleted[entity.id] = entity
    }

    override suspend fun insertToCompleted(entity: TodoEntity) {
        completed[entity.id] = entity
    }

    override suspend fun get(id: Id): TodoEntity? = completed[id] ?: notCompleted[id]

    override suspend fun update(entity: TodoEntity) {
        if (notCompleted.containsKey(entity.id)) {
            notCompleted[entity.id] = entity
        } else {
            completed[entity.id] = entity
        }
    }

    override suspend fun deleteFromCompleted(id: Id) {
        completed.remove(id)
    }

    override suspend fun deleteFromNotCompleted(id: Id) {
        notCompleted.remove(id)
    }

    override suspend fun isCompleted(id: Id): Boolean = completed.containsKey(id)
}