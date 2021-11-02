package blog.mateuszgrabarski.todo.domain.usecases.lists

import blog.mateuszgrabarski.todo.domain.models.TodoList
import blog.mateuszgrabarski.todo.domain.usecases.utils.UseCase

interface GetAllTodoLists : UseCase<List<TodoList>>