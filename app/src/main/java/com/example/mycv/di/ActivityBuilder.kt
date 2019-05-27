package com.example.mycv.di

import com.example.mycv.activities.main.MainActivity
import com.example.mycv.core.viewModel.ViewModelFactoryModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class])
    internal abstract fun bindMainActivity(): MainActivity
}


