package com.example.mycv.core.viewModel

import androidx.lifecycle.ViewModel
import com.example.mycv.api.ServerAPI
import com.example.mycv.api.ServerAPIImpl
import com.example.mycv.fragments.briefCV.BriefCVModule
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module(includes = [
    BriefCVModule::class])
class ViewModelFactoryModule {

    @Provides
    fun provideServerAPI(): ServerAPI = ServerAPIImpl

    @Provides
    fun provideViewModelFactory(
            providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): MyViewModelFactory = MyViewModelFactory(providers)
}