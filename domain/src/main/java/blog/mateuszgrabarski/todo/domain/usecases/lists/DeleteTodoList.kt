package blog.mateuszgrabarski.todo.domain.usecases.lists

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.usecases.utils.ArgumentedUseCase

interface DeleteTodoList : ArgumentedUseCase<Id, Result<Boolean>> {

    companion object {
        const val ERROR_LIST_NOT_FOUND = "list with id from arguments not found"
    }
}