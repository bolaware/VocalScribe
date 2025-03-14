package com.bolaware.data.di

import android.content.Context
import androidx.room.Room
import com.bolaware.data.database.AppDatabase
import com.bolaware.data.database.TranscriptDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
    }

    @Provides
    fun provideTranscriptDao(database: AppDatabase): TranscriptDao {
        return database.transcriptDao()
    }
}
