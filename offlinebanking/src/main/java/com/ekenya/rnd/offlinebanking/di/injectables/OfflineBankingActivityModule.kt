package com.ekenya.rnd.offlinebanking.di.injectables

import androidx.lifecycle.ViewModel
import com.ekenya.rnd.baseapp.di.ViewModelKey
import com.ekenya.rnd.offlinebanking.MainActivity
import com.ekenya.rnd.offlinebanking.ui.dashboard.DashboardViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class OfflineBankingActivityModule {

    /**
     * Main Activity
     */
    ///////////////////////////////////////////////////////////////////////////////////
    @ContributesAndroidInjector(modules = [TourismMainActivityModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @Module
    abstract class TourismMainActivityModule {
        @Binds
        @IntoMap
        @ViewModelKey(DashboardViewModel::class)
        abstract fun bindLoginViewModel(viewModel: DashboardViewModel): ViewModel
    }
    ///////////////////////////////////////////////////////////////////////////////////
//
//    /**
//     * Slider
//     */
//    ///////////////////////////////////////////////////////////////////////////////////
//    @ContributesAndroidInjector(modules = [SmeSliderActivityModule::class])
//    abstract fun contributeSliderActivity(): SliderActivity
//
//    @Module
//    abstract class SmeSliderActivityModule {
//
//        @Binds
//        @IntoMap
//        @ViewModelKey(CountryViewModel::class)
//        abstract fun bindCountryViewModel(viewModel: CountryViewModel): ViewModel
//    }

    ///////////////////////////////////////////////////////////////////////////////////

    //LIST ALL OTHER ACTIVITIES IN THIS MODULE
}
