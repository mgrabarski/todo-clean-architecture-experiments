package blog.mateuszgrabarski.todo.domain.usecases.lists

import blog.mateuszgrabarski.todo.domain.models.Id
import blog.mateuszgrabarski.todo.domain.usecases.lists.DeleteTodoList.Arguments
import blog.mateuszgrabarski.todo.domain.usecases.utils.ArgumentedUseCase
import blog.mateuszgrabarski.todo.domain.usecases.utils.UseCaseResult

interface DeleteTodoList : ArgumentedUseCase<Arguments, UseCaseResult<Boolean>> {

    data class Arguments(
        val listId: Id
    )

    companion object {
        const val ERROR_LIST_NOT_FOUND = "list with id from arguments not found"
        const val ERROR_UNKNOWN = "something went wrong"
    }
}