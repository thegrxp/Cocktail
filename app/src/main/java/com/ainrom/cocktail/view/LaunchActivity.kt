package com.ainrom.cocktail.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ainrom.cocktail.R
import com.ainrom.cocktail.utils.PermissionsDelegate
import com.ainrom.cocktail.utils.replaceFragment
import com.ainrom.cocktail.view.streams.StreamsListFragment
import com.google.android.gms.cast.framework.CastContext
import kotlinx.android.synthetic.main.activity_launch.*

class LaunchActivity : AppCompatActivity() {

    private val permissionsDelegate: PermissionsDelegate by lazy { PermissionsDelegate(this) }
    internal lateinit var castContext: CastContext

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        castContext = CastContext.getSharedInstance(this)

        // This bottom navigation bar is not necessary with only one item, let's imagine there are more.
        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_streams -> {
                    this@LaunchActivity.replaceFragment(StreamsListFragment(), R.id.fragment_container)
                    true
                }
                else -> { true }
            }
        }

        navigation.selectedItemId = R.id.navigation_streams
    }

    override fun onResume() {
        super.onResume()
        if (!permissionsDelegate.hasPermissions()) {
            permissionsDelegate.requestPermissions()
        }
    }
}
