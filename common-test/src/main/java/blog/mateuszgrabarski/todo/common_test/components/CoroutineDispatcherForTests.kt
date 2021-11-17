package blog.mateuszgrabarski.todo.common_test.components

import blog.mateuszgrabarski.todo.common.components.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class CoroutineDispatcherForTests(
    private val testCoroutineDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : DispatcherProvider {

    override fun main(): CoroutineDispatcher = testCoroutineDispatcher

    override fun io(): CoroutineDispatcher = testCoroutineDispatcher

    override fun default(): CoroutineDispatcher = testCoroutineDispatcher
}