package com.ainrom.cocktail.view.streams

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.ainrom.cocktail.R
import com.ainrom.cocktail.databinding.ActivityPlayerBinding
import com.ainrom.cocktail.di.Injector
import com.ainrom.cocktail.view.ViewModelFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import javax.inject.Inject

class PlayerActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var streamsViewModel: StreamsViewModel
    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().inject(this)

        streamsViewModel = ViewModelProviders.of(
            this@PlayerActivity,
            this.viewModelFactory
        ).get(StreamsViewModel::class.java)

        binding = DataBindingUtil.setContentView(this@PlayerActivity, R.layout.activity_player)
        binding.lifecycleOwner = this@PlayerActivity
        binding.viewModel = streamsViewModel
    }

    override fun onResume() {
        super.onResume()
        setScreenParams()

        val player = ExoPlayerFactory.newSimpleInstance(this)
        val uri = Uri.parse(streamsViewModel.stream.value?.videoUri)
        val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "cocktail"))
        val videoSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
        player.prepare(videoSource)
        player.playWhenReady = true
        binding.playerView.player = player
    }

    private fun setScreenParams() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        window.decorView.apply {
            systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }

    override fun onDestroy() {
        binding.playerView.player.release()
        super.onDestroy()
    }
}
