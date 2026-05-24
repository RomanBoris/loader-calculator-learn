package com.pobezhkin.loadercalculator.di

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
import com.pobezhkin.loadercalculator.data.workshift.MiniTruckDao
import com.pobezhkin.loadercalculator.data.workshift.ShiftHistoryDao
import com.pobezhkin.loadercalculator.data.workshift.UploadTruckDao
import com.pobezhkin.loadercalculator.data.workshift.repository.LoaderRepositoryImpl
import com.pobezhkin.loadercalculator.data.workshift.repository.ShiftHistoryRepositoryImpl
import com.pobezhkin.loadercalculator.domain.repository.LoaderRepository
import com.pobezhkin.loadercalculator.domain.repository.SettingsRepository
import com.pobezhkin.loadercalculator.domain.repository.ShiftHistoryRepository
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
                """.trimIndent()
            )
        }
    }

    private val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
                CREATE TABLE IF NOT EXISTS shift_history (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    savedDate INTEGER NOT NULL,
                    hoursWorked REAL NOT NULL,
                    totalLoadEo INTEGER,
                    totalLoadFzEo INTEGER,
                    totalUploadEo INTEGER,
                    totalMiniEo INTEGER,
                    totalMiniFzEo INTEGER
                )
                """.trimIndent()
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
        ).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
            .build()
    }

    @Provides
    @Singleton
    fun provideItemDao(loaderDataBase: LoaderDataBase): LoadedTruckDao = loaderDataBase.truckDao()

    @Provides
    @Singleton
    fun provideUploadTruckDao(loaderDataBase: LoaderDataBase): UploadTruckDao =
        loaderDataBase.uploadTruckDao()

    @Provides
    @Singleton
    fun provideMiniTruckDao(loaderDataBase: LoaderDataBase): MiniTruckDao =
        loaderDataBase.miniTruckDao()

    @Provides
    @Singleton
    fun provideShiftHistoryDao(db: LoaderDataBase): ShiftHistoryDao = db.shiftHistoryDao()

    @Provides
    @Singleton
    fun provideItemRepository(
        loadedTruckDao: LoadedTruckDao,
        uploadTruckDao: UploadTruckDao,
        miniTruckDao: MiniTruckDao
    ): LoaderRepository = LoaderRepositoryImpl(loadedTruckDao, uploadTruckDao, miniTruckDao)

    @Provides
    @Singleton
    fun provideShiftHistoryRepository(dao: ShiftHistoryDao): ShiftHistoryRepository =
        ShiftHistoryRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore

    @Provides
    @Singleton
    fun provideSettingsRepository(dataStore: DataStore<Preferences>): SettingsRepository =
        SettingsRepositoryImpl(dataStore)

    @Provides
    @Singleton
    fun provideObserveDailyPerformanceUseCase(
        loaderRepository: LoaderRepository
    ): ObserveDailyPerformanceUseCase = ObserveDailyPerformanceUseCase(loaderRepository)
}
