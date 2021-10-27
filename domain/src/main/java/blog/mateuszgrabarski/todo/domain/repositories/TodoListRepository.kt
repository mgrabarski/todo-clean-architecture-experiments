package blog.mateuszgrabarski.todo.domain.repositories

import blog.mateuszgrabarski.todo.domain.models.TodoList

interface TodoListRepository {
    suspend fun saveList(newList: TodoList)
}