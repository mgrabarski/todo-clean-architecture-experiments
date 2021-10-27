package blog.mateuszgrabarski.todo.data.store

interface TodoListRemoteDataSource {
    suspend fun insert()
}