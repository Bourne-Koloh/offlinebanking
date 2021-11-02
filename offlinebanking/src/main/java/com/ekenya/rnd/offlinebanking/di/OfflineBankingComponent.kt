package com.ekenya.rnd.offlinebanking.di

import com.ekenya.rnd.baseapp.di.AppComponent
import com.ekenya.rnd.baseapp.di.ModuleScope
import com.ekenya.rnd.baseapp.di.injectables.ViewModelModule
import com.ekenya.rnd.offlinebanking.di.injectables.OfflineBankingActivityModule
import com.ekenya.rnd.offlinebanking.di.injectables.OfflineBankingFragmentModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@ModuleScope
@Component(
    dependencies = [
        AppComponent::class
    ],
    modules = [
        AndroidSupportInjectionModule::class,
        OfflineBankingActivityModule::class,
        OfflineBankingFragmentModule::class,
        ViewModelModule::class
    ]
)
interface OfflineBankingComponent {
    fun inject(injector: OfflineBankingInjector)
}