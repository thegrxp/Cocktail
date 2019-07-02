package com.ainrom.cocktail.di

import com.ainrom.cocktail.App

class Injector private constructor() {
    companion object {
        fun get(): AppComponent = App.component
    }
}