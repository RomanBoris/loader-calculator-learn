package com.pobezhkin.loadercalculator.DI

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pobezhkin.loadercalculator.data.settings.SettingsRepositoryImpl
import com.pobezhkin.loadercalculator.data.workshift.LoadedTruckDao
import com.pobezhkin.loadercalculator.data.workshift.LoaderDataBase
import com.pobezhkin.loadercalculator.data.workshift.repository.LoaderRepositoryImpl
import com.pobezhkin.loadercalculator.data.workshift.repository.MiniTruckDao
import com.pobezhkin.loadercalculator.data.workshift.repository.UploadTruckDao
import com.pobezhkin.loadercalculator.domain.repository.LoaderRepository
import com.pobezhkin.loadercalculator.domain.repository.SettingsRepository
import com.pobezhkin.loadercalculator.domain.usecase.ObserveDailyPerformanceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


@Module
@InstallIn(SingletonComponent::class)
object DiModule {

    // 1. Сначала объявляем миграцию
    /* private val MIGRATION_1_2 = object : Migration(1, 2) {
         override fun migrate(database: SupportSQLiteDatabase) {
             database.execSQL("""
                 CREATE TABLE uploader_trucks (
                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                     count INTEGER NOT NULL,
                     timestamp INTEGER DEFAULT 0
                 )
             """)
         }
     }*/

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
        CREATE TABLE IF NOT EXISTS uploader_trucks (
        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            upload_eo INTEGER NOT NULL
)
""".trimIndent()
            )
        }
    }

    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
            CREATE TABLE mini_loaded_trucks (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                miniTruck_eo INTEGER NOT NULL,
                miniTruck_fz_eo INTEGER NOT NULL
            )
        """
            )
        }
    }

    @Provides
    @Singleton
    fun providesLoaderDataBase(@ApplicationContext context: Context): LoaderDataBase {
        return Room.databaseBuilder(
            context,
            LoaderDataBase::class.java,
            "loaded_trucks.db"
        ).addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }


    @Provides
    @Singleton
    fun provideItemDao(loaderDataBase: LoaderDataBase): LoadedTruckDao {
        return loaderDataBase.truckDao()
    }

    @Provides
    @Singleton
    fun provideUploadTruckDao(loaderDataBase: LoaderDataBase): UploadTruckDao {
        return loaderDataBase.uploadTruckDao()
    }

    @Provides
    @Singleton
    fun provideMiniTruckDao(loaderDataBase: LoaderDataBase): MiniTruckDao {
        return loaderDataBase.miniTruckDao()
    }

    @Provides
    @Singleton
    fun provideItemRepository(
        loadedTruckDao: LoadedTruckDao,
        uploadTruckDao: UploadTruckDao,
        miniTruckDao: MiniTruckDao
    ): LoaderRepository {
        return LoaderRepositoryImpl(
            loadedTruckDao,
            uploadTruckDao,
            miniTruckDao
        )
    }

    @Provides
    @Singleton
    fun provideObserveDailyPerformanceUseCase(
        loaderRepository: LoaderRepository
    ): ObserveDailyPerformanceUseCase = ObserveDailyPerformanceUseCase(loaderRepository)

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore

    @Provides
    @Singleton
    fun provideSettingsRepository(dataStore: DataStore<Preferences>): SettingsRepository =
        SettingsRepositoryImpl(dataStore)

}