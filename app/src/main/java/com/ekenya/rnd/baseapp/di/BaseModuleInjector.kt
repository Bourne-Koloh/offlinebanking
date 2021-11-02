package com.ekenya.rnd.baseapp.di

import android.app.Activity
import androidx.fragment.app.Fragment
import com.ekenya.rnd.baseapp.BaseApp
import dagger.android.DispatchingAndroidInjector

interface BaseModuleInjector {

    fun inject(app: BaseApp)

    fun activityInjector(): DispatchingAndroidInjector<Activity>

    fun fragmentInjector(): DispatchingAndroidInjector<Fragment>
}
