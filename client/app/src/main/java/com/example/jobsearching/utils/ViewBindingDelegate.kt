package com.example.jobsearching.utils

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

inline fun <reified T : ViewBinding> Fragment.viewBinding(
    noinline binder: (View) -> T
): Lazy<T> {
    return lazy {
        binder(requireView())
    }
}

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline binder: (LayoutInflater) -> T
): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        binder(layoutInflater)
    }
}