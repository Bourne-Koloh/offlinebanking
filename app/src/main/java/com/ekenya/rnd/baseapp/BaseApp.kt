package com.ekenya.rnd.baseapp

import android.app.Activity
import androidx.fragment.app.Fragment
import com.ekenya.rnd.baseapp.di.AppComponent
import com.ekenya.rnd.baseapp.di.BaseModuleInjector
import com.ekenya.rnd.baseapp.di.DaggerAppComponent
import com.ekenya.rnd.baseapp.di.helpers.features.FeatureModule
import com.ekenya.rnd.common.abstractions.BaseApplication
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class BaseApp: BaseApplication(), HasActivityInjector, HasSupportFragmentInjector {

    // ActivityInjector / FragmentInjector used in the main module
    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>

    // List of ActivityInjector / FragmentInjector used in each Feature module
    private val moduleActivityInjectors = mutableListOf<DispatchingAndroidInjector<Activity>>()
    private val moduleFragmentInjectors = mutableListOf<DispatchingAndroidInjector<Fragment>>()

    // AndroidInjector <Activity> that actually injects
    private val activityInjector = AndroidInjector<Activity> { instance ->
        // If true is returned by maybeInject, Inject is successful

        // Main module
        if (dispatchingActivityInjector.maybeInject(instance)) {
            return@AndroidInjector
        }

        // Each Feature module
        moduleActivityInjectors.forEach { injector ->
            if (injector.maybeInject(instance)) {
                return@AndroidInjector
            }
        }
        throw IllegalStateException("Injector not found for $instance")
    }

    // AndroidInjector <Fragment> that actually injects each
    private val fragmentInjector = AndroidInjector<Fragment> { instance ->
        // If true is returned by maybeInject, Inject is successful

        // Main module
        if (dispatchingFragmentInjector.maybeInject(instance)) {
            return@AndroidInjector
        }

        // Each Feature module
        moduleFragmentInjectors.forEach { injector ->
            if (injector.maybeInject(instance)) {
                return@AndroidInjector
            }
        }
        throw IllegalStateException("Injector not found for $instance")
    }

    // Set for determining whether the Injector of the Feature module has been generated
    private val injectedModules = mutableSetOf<FeatureModule>()

    // Used from AppComponent and Component of each Feature module
    val appComponent by lazy {
        DaggerAppComponent.builder().create(this) as AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        // Returns the actual Injector
        return activityInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        // Returns the actual Injector
        return fragmentInjector
    }

    // Add Injector for Feature module
    // Called just before the Feature module is used after installation
    fun addModuleInjector(module: FeatureModule) {
        if (injectedModules.contains(module)) {
            // Do nothing if added
            return
        }

        // Generate Injector for Feature module
        val clazz = Class.forName(module.injectorName)
        val moduleInjector = clazz.newInstance() as BaseModuleInjector
        // Inject Dispatching Android Injector of Injector of Feature module
        moduleInjector.inject(this)

        // Add to list
        moduleActivityInjectors.add(moduleInjector.activityInjector())
        moduleFragmentInjectors.add(moduleInjector.fragmentInjector())

        injectedModules.add(module)
    }
}