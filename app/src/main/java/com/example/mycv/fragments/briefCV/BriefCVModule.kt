package com.example.mycv.fragments.briefCV

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.mycv.api.ServerAPI
import com.example.mycv.core.viewModel.MyViewModelFactory
import com.example.mycv.core.viewModel.ViewModelKey
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [BriefCVModule.ProvideViewModel::class])
abstract class BriefCVModule {

    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun bind(): BriefCVFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(BriefCVViewModel::class)
        fun provideBriefCVViewModel(serverAPI: ServerAPI): ViewModel =
                BriefCVViewModel(serverAPI)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideBriefCVViewModel(
                factory: MyViewModelFactory,
                target: BriefCVFragment
        ): BriefCVModel = ViewModelProviders.of(target, factory).get(BriefCVViewModel::class.java)

    }

}