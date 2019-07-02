package com.ainrom.cocktail.view.streams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ainrom.cocktail.data.Engine
import com.ainrom.cocktail.data.Stream
import com.ainrom.cocktail.utils.Event
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StreamsViewModel @Inject constructor(private val engine: Engine) : ViewModel(), StreamsActionsViewModel {

    override val streams: LiveData<List<Stream>>
        get() = engine.streams

    private val _stream = MutableLiveData<Stream>()
    override val stream: LiveData<Stream>
        get() = _stream

    private val _itemClicked = MutableLiveData<Event<Boolean>>()
    override val itemClicked: LiveData<Event<Boolean>>
        get() = _itemClicked

    override fun openStream(exercise: Stream) {
        _stream.value = exercise
        _itemClicked.value = Event(true)
    }

    fun getStreams() { viewModelScope.launch { engine.getStreams() } }
}

private interface StreamsActionsViewModel {
    val stream: LiveData<Stream>
    val streams: LiveData<List<Stream>>
    val itemClicked: LiveData<Event<Boolean>>
    fun openStream(exercise: Stream)
}