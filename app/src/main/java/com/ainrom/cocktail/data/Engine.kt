package com.ainrom.cocktail.data

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Engine @Inject constructor() {

    var streams = MutableLiveData<List<Stream>>()

    // Simulate network request to get streams list.
    suspend fun getStreams() = coroutineScope {
        val fakeStreams = async { getFakeOnlineStreams() }
        try {
            streams.postValue(fakeStreams.await())
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}