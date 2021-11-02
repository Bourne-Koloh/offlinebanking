package com.ekenya.rnd.support.di.injectables

import androidx.lifecycle.ViewModel
import com.ekenya.rnd.baseapp.di.ViewModelKey
import com.ekenya.rnd.support.ui.FirstFragment
import com.ekenya.rnd.support.ui.MainViewModel
import com.ekenya.rnd.support.ui.SecondFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SupportFragmentModule {

    @ContributesAndroidInjector(modules = [TourismFirstFragmentModule::class])
    abstract fun contributeFirstFragment(): FirstFragment


    @Module
    abstract class TourismFirstFragmentModule {
        @Binds
        @IntoMap
        @ViewModelKey(MainViewModel::class)
        abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
    }


    @ContributesAndroidInjector(modules = [TourismFirstFragmentModule::class])
    abstract fun contributeSecondFragment(): SecondFragment

    @Module
    abstract class TourismSecondFragmentModule {
        @Binds
        @IntoMap
        @ViewModelKey(MainViewModel::class)
        abstract fun bindPageViewModel(viewModel: MainViewModel): ViewModel
    }
    //LIST THE OTHER INJECTABLE FRAGMENTS AS ABOVE
}
