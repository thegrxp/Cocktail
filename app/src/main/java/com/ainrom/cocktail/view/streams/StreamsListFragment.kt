package com.ainrom.cocktail.view.streams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.ainrom.cocktail.R
import com.ainrom.cocktail.databinding.FragmentStreamsListBinding
import com.ainrom.cocktail.di.Injector
import com.ainrom.cocktail.utils.EventObserver
import com.ainrom.cocktail.utils.replaceFragment
import com.ainrom.cocktail.view.ViewModelFactory
import javax.inject.Inject

class StreamsListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var streamsViewModel: StreamsViewModel
    private lateinit var binding: FragmentStreamsListBinding
    private lateinit var adapter: StreamsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Injector.get().inject(this)

        streamsViewModel = ViewModelProviders.of(this@StreamsListFragment, this.viewModelFactory)
            .get(StreamsViewModel::class.java)

        adapter = StreamsAdapter(streamsViewModel)

        binding = FragmentStreamsListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@StreamsListFragment
            viewModel = streamsViewModel
            rvStreams.adapter = adapter
            rvStreams.layoutManager = GridLayoutManager(view?.context, 3)
        }

        streamsViewModel.getStreams()

        streamsViewModel.itemClicked.observe(this, EventObserver {
            val streamFragment = StreamFragment()
            streamFragment.enterTransition = TransitionInflater
                .from(activity)
                .inflateTransition(R.transition.trans_bottom)
                .setDuration(100)

            fragmentManager?.replaceFragment(streamFragment, R.id.fragment_container)
        })

        return binding.root
    }
}
