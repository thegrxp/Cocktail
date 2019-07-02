package com.ainrom.cocktail.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ainrom.cocktail.App

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    popBackStack()
    beginTransaction().func().commit()
}

fun <F> AppCompatActivity.replaceFragment(fragment: F, id: Int) where F : Fragment {
    supportFragmentManager.inTransaction {
        replace(id, fragment)
    }
}

fun <F> FragmentManager.replaceFragment(fragment: F, id: Int) where F : Fragment {
    inTransaction {
        replace(id, fragment).addToBackStack("STREAM_BACK")
    }
}

inline fun <reified T : Any> Context.launchActivity(options: Bundle? = null, noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent, options)
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)

fun String.toast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun Int.setViewColor(view: View) {
    view.setBackgroundColor(ContextCompat.getColor(App.instance.applicationContext, this))
}
