package blog.mateuszgrabarski.todo.data.di

import blog.mateuszgrabarski.todo.data.model.mappers.specific.TodoListMapper
import blog.mateuszgrabarski.todo.data.model.mappers.specific.TodoMapper
import org.koin.dsl.module

val dataModule = module {
    factory { TodoListMapper() }
    factory { TodoMapper() }
}