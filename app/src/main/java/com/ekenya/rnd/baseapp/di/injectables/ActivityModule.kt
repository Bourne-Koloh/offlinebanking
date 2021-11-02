package com.ekenya.rnd.baseapp.di.injectables

import androidx.lifecycle.ViewModel
import com.ekenya.rnd.baseapp.di.ViewModelKey
import com.ekenya.rnd.baseapp.ui.SplashActivity
import com.ekenya.rnd.baseapp.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun contributeSplashActivity(): SplashActivity


    @Module
    abstract class SplashActivityModule {
        @Binds
        @IntoMap
        @ViewModelKey(MainViewModel::class)
        abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    }

//    @ContributesAndroidInjector(modules = [MainActivityModule::class])
//    abstract fun contributeMainActivity(): MainActivity
//
//    @Module
//    abstract class MainActivityModule {
//        @Binds
//        @IntoMap
//        @ViewModelKey(MainViewModel::class)
//        abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
//    }
}
