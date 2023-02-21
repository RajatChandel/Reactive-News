package `in`.rchandel.reactivenews

import `in`.rchandel.reactivenews.di.ApplicationComponent
import `in`.rchandel.reactivenews.di.DaggerApplicationComponent
import android.app.Application
import dagger.Component

class ReactiveNewsApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }

}