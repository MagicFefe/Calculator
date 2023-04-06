package com.swaptech.calculator.di.data.db

import android.content.Context
import androidx.room.Room
import com.swaptech.calculator.data.Database
import com.swaptech.calculator.presentation.util.DB_NAME
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(context: Context): Database = Room
        .databaseBuilder(context, Database::class.java, DB_NAME).build()
}