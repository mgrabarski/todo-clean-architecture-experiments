package blog.mateuszgrabarski.todo.data.store.network

import blog.mateuszgrabarski.todo.domain.models.TodoList

interface TodoListRemoteDataSource {
    suspend fun insert(newList: TodoList)
}