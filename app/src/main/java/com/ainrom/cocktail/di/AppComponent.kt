package com.ainrom.cocktail.di

import android.app.Application
import com.ainrom.cocktail.view.streams.PlayerActivity
import com.ainrom.cocktail.view.streams.StreamsListFragment
import com.ainrom.cocktail.view.streams.StreamFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        ContextModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder
        fun build(): AppComponent
    }

    fun inject(detailsFragment: StreamFragment)
    fun inject(listFragment: StreamsListFragment)
    fun inject(playerActivity: PlayerActivity)
}