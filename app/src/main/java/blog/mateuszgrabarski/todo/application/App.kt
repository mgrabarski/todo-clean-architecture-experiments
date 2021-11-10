package blog.mateuszgrabarski.todo.application

import android.app.Application
import blog.mateuszgrabarski.todo.application.di.components
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        configureDI()
    }

    private fun configureDI() = startKoin {
        androidContext(this@App)
        fragmentFactory()
        modules(components)
    }
}