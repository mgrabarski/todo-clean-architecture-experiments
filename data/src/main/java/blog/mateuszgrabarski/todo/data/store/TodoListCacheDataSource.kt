package blog.mateuszgrabarski.todo.data.store

interface TodoListCacheDataSource {
    suspend fun insert()
}