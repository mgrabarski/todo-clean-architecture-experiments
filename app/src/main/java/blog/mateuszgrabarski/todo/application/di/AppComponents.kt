package blog.mateuszgrabarski.todo.application.di

import blog.mateuszgrabarski.todo.data.di.dataModule
import blog.mateuszgrabarski.todo.domain.di.domainModule

val components = listOf(
    domainModule,
    dataModule
)