package com.ainrom.cocktail.view.streams

import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ainrom.cocktail.App
import com.ainrom.cocktail.databinding.FragmentStreamBinding
import com.ainrom.cocktail.di.Injector
import com.ainrom.cocktail.utils.launchActivity
import com.ainrom.cocktail.utils.replaceFragment
import com.ainrom.cocktail.utils.toast
import com.ainrom.cocktail.view.LaunchActivity
import com.ainrom.cocktail.view.ViewModelFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.cast.CastPlayer
import com.google.android.exoplayer2.ext.cast.SessionAvailabilityListener
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaMetadata
import com.google.android.gms.cast.MediaQueueItem
import com.google.android.gms.cast.framework.CastButtonFactory
import com.google.android.gms.common.images.WebImage
import javax.inject.Inject
import com.ainrom.cocktail.R

class StreamFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var streamsViewModel: StreamsViewModel
    private lateinit var binding: FragmentStreamBinding
    private lateinit var castPlayer: CastPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Injector.get().inject(this)

        streamsViewModel = ViewModelProviders.of(
            this@StreamFragment,
            this.viewModelFactory
        ).get(StreamsViewModel::class.java)

        binding = FragmentStreamBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@StreamFragment
            viewModel = streamsViewModel

            setHasOptionsMenu(true)
            (activity as LaunchActivity).setSupportActionBar(toolbar)
            toolbar.title = null

            fabPlay.setOnClickListener { it.context.launchActivity<PlayerActivity>() }
            ivStreamBack.setOnClickListener {
                fragmentManager?.replaceFragment(
                    StreamsListFragment(),
                    R.id.fragment_container
                )
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (streamsViewModel.stream.value != null) {
            // Set the cast player
            val movieMetadata = MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE)
            movieMetadata.putString(MediaMetadata.KEY_TITLE, streamsViewModel.stream.value?.name)
            movieMetadata.addImage(WebImage(Uri.parse(streamsViewModel.stream.value?.imageUri)))
            val mediaInfo = MediaInfo.Builder(streamsViewModel.stream.value?.videoUri)
                .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                .setContentType(MimeTypes.VIDEO_UNKNOWN)
                .setMetadata(movieMetadata).build()

            val mediaItems = arrayOf(MediaQueueItem.Builder(mediaInfo).build())

            castPlayer = CastPlayer((activity as LaunchActivity).castContext)
            castPlayer.setSessionAvailabilityListener(object: SessionAvailabilityListener {
                override fun onCastSessionAvailable() {
                    castPlayer.loadItems(mediaItems, 0, 0L, Player.REPEAT_MODE_OFF)
                }

                override fun onCastSessionUnavailable() {
                    getString(R.string.toast_cast_unavailable).toast(view.context)
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.stream, menu)
        CastButtonFactory.setUpMediaRouteButton(App.instance.applicationContext, menu, R.id.media_route_menu_item)
    }

    override fun onDestroy() {
        castPlayer.release()
        super.onDestroy()
    }
}
