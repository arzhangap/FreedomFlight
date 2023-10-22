package com.arzhang.project.freedomflight.data.di

import android.content.Context
import com.arzhang.project.freedomflight.data.FlightDao
import com.arzhang.project.freedomflight.data.FlightDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): FlightDatabase {
        return FlightDatabase.getDatabase(context)
    }

    @Provides
    fun provideFlightDao(appDatabase: FlightDatabase): FlightDao {
        return appDatabase.flightDao()
    }

}