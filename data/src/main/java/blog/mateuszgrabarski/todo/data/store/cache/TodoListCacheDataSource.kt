package blog.mateuszgrabarski.todo.data.store.cache

import blog.mateuszgrabarski.todo.domain.models.TodoList

interface TodoListCacheDataSource {
    suspend fun insert(newList: TodoList)
}