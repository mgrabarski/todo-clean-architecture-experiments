package blog.mateuszgrabarski.todo.domain.di

import blog.mateuszgrabarski.todo.domain.data.factories.TodoFactory
import blog.mateuszgrabarski.todo.domain.data.factories.TodoListFactory
import blog.mateuszgrabarski.todo.domain.data.validators.TodoDescriptionValidator
import blog.mateuszgrabarski.todo.domain.data.validators.TodoListNameValidator
import blog.mateuszgrabarski.todo.domain.usecases.lists.*
import blog.mateuszgrabarski.todo.domain.usecases.lists.impl.*
import blog.mateuszgrabarski.todo.domain.usecases.todo.*
import blog.mateuszgrabarski.todo.domain.usecases.todo.impl.*
import org.koin.dsl.module

val domainModule = module {
    factory { TodoFactory() }
    factory { TodoListFactory() }
    factory { TodoDescriptionValidator() }
    factory { TodoListNameValidator() }

    factory<CreateNewTodoList> { CreateNewTodoListImpl(get(), get(), get()) }
    factory<DeleteTodoList> { DeleteTodoListImpl(get()) }
    factory<GetAllTodoLists> { GetAllTodoListsImpl(get()) }
    factory<GetTodoList> { GetTodoListImpl(get()) }
    factory<UpdateTodoList> { UpdateTodoListImpl(get(), get()) }

    factory<AddTodoToList> { AddTodoToListImpl(get(), get(), get(), get()) }
    factory<DeleteTodo> { DeleteTodoImpl(get()) }
    factory<MarkTodoAsCompleted> { MarkTodoAsCompletedImpl(get()) }
    factory<MarkTodoAsNotCompleted> { MarkTodoAsNotCompletedImpl(get()) }
    factory<UpdateTodo> { UpdateTodoImpl(get(), get()) }
}