package com.swaptech.calculator.di.data

import com.swaptech.calculator.di.data.db.DaoModule
import com.swaptech.calculator.di.data.db.DatabaseModule
import com.swaptech.calculator.di.data.repository.RepositoryModule
import dagger.Module

@Module(
    includes = [
        DaoModule::class, DatabaseModule::class, RepositoryModule::class
    ]
)
class DataModule
