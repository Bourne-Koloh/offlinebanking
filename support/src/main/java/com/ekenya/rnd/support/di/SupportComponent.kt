package com.ekenya.rnd.support.di

import com.ekenya.rnd.baseapp.di.AppComponent
import com.ekenya.rnd.baseapp.di.ModuleScope
import com.ekenya.rnd.baseapp.di.injectables.ViewModelModule
import com.ekenya.rnd.support.di.injectables.SupportActivityModule
import com.ekenya.rnd.support.di.injectables.SupportFragmentModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@ModuleScope
@Component(
    dependencies = [
        AppComponent::class
    ],
    modules = [
        AndroidSupportInjectionModule::class,
        SupportActivityModule::class,
        SupportFragmentModule::class,
        ViewModelModule::class
    ]
)
interface SupportComponent {
    fun inject(injector: SupportInjector)
}