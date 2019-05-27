package com.example.mycv.di

import com.example.mycv.MyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, ActivityBuilder::class])
interface AppComponent  {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: MyApplication): Builder

        fun build(): AppComponent
    }

    fun inject(fragment: MyApplication)
}