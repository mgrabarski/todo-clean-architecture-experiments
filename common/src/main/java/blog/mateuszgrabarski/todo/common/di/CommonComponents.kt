package blog.mateuszgrabarski.todo.common.di

import blog.mateuszgrabarski.todo.common.components.DispatcherProvider
import blog.mateuszgrabarski.todo.common.components.impl.DispatcherProviderImpl
import org.koin.dsl.module

val commonModule = module {
    factory<DispatcherProvider> { DispatcherProviderImpl() }
}