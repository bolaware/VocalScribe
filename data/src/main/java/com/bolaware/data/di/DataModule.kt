package com.bolaware.data.di

import android.content.Context
import androidx.room.Room
import com.bolaware.data.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val dataModule = module {
    single {
        val context: Context = androidContext()
        Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
    }

    single {
        get<AppDatabase>().transcriptDao()
    }
}