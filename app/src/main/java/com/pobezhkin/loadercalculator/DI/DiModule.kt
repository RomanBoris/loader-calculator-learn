package com.pobezhkin.loadercalculator.DI

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pobezhkin.loadercalculator.data.workshift.LoadedTruckDao
import com.pobezhkin.loadercalculator.data.workshift.LoaderDataBase
import com.pobezhkin.loadercalculator.data.workshift.repository.LoaderRepositoryImpl
import com.pobezhkin.loadercalculator.data.workshift.repository.UploadTruckDao
import com.pobezhkin.loadercalculator.domain.repository.LoaderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object DiModule {

    // 1. Сначала объявляем миграцию
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("""
                CREATE TABLE upload_records (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    count INTEGER NOT NULL,
                    timestamp INTEGER DEFAULT 0
                )
            """)
        }
    }

    @Provides
    @Singleton
    fun providesLoaderDataBase(@ApplicationContext context : Context) : LoaderDataBase {
        return Room.databaseBuilder(
            context,
            LoaderDataBase::class.java,
            "loaded_trucks.db"
        ).addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    @Singleton
    fun provideItemDao(loaderDataBase : LoaderDataBase ): LoadedTruckDao {
        return loaderDataBase.truckDao()
    }

    @Provides
    @Singleton
    fun provideUploadTruckDao(loaderDataBase: LoaderDataBase): UploadTruckDao {
        return loaderDataBase.uploadTruckDao()
    }

    @Provides
    @Singleton
    fun provideItemRepository(loadedTruckDao : LoadedTruckDao, uploadTruckDao : UploadTruckDao ): LoaderRepository {
        return LoaderRepositoryImpl(
            loadedTruckDao,
            uploadTruckDao
        )
    }

}