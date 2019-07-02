package com.ainrom.cocktail.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ainrom.cocktail.view.ViewModelFactory
import com.ainrom.cocktail.view.streams.StreamsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(StreamsViewModel::class)
    abstract fun bindStreamsViewModel(streamsViewModel: StreamsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}