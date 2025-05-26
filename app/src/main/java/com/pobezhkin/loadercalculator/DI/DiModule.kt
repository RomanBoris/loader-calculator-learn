package com.pobezhkin.loadercalculator.DI

import android.content.Context
import androidx.room.Room
import com.pobezhkin.loadercalculator.data.workshift.LoadedTruckDao
import com.pobezhkin.loadercalculator.data.workshift.LoaderDataBase
import com.pobezhkin.loadercalculator.data.workshift.repository.LoaderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object DiModule {

    @Provides
    @Singleton
    fun providesLoaderDataBase(@ApplicationContext context : Context) : LoaderDataBase {
        return Room.databaseBuilder(
            context,
            LoaderDataBase::class.java,
            "loaded_trucks.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideItemDao(loaderDataBase : LoaderDataBase ): LoadedTruckDao {
        return loaderDataBase.truckDao()
    }

    @Provides
    @Singleton
    fun provideItemRepository(loadedTruckDao : LoadedTruckDao ): LoaderRepository {
        return LoaderRepository(loadedTruckDao)
    }




}