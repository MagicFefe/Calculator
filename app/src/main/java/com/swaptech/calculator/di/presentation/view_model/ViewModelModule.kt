package com.swaptech.calculator.di.presentation.view_model

import androidx.lifecycle.ViewModel
import com.swaptech.calculator.presentation.screens.history.HistoryScreenViewModel
import com.swaptech.calculator.presentation.screens.home.HomeScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @Singleton
    @CreateWithViewModelFactory(HistoryScreenViewModel::class)
    fun bindHistoryScreenViewModel(historyScreenViewModel: HistoryScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @Singleton
    @CreateWithViewModelFactory(HomeScreenViewModel::class)
    fun bindHomeScreenViewModel(homeScreenViewModel: HomeScreenViewModel): ViewModel
}
