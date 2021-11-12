package blog.mateuszgrabarski.todo.data.di

import blog.mateuszgrabarski.todo.data.model.mappers.specific.DomainEntityMapper
import org.koin.dsl.module

val dataModule = module {
    factory { DomainEntityMapper() }
}