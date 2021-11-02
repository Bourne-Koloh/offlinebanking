package com.ekenya.rnd.baseapp.di

import android.app.Application
import android.content.Context
import com.ekenya.rnd.baseapp.BaseApp
import com.ekenya.rnd.common.repo.SampleDataRepo
import com.ekenya.rnd.common.repo.SampleRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(app: BaseApp): Context {
        return app
    }

    @Singleton
    @Provides
    fun provideSampleRepository(context: Context): SampleRepository {
        return SampleDataRepo(context)
    }

    @Singleton
    @Provides
    fun provideApp(context: Context): Application {
        return context.applicationContext as Application
    }
}