package com.example.demoapp

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment

abstract class NavigationFragment(): Fragment() {
    protected val mainActivity: MainActivity get() = (super.getActivity() as MainActivity)
    protected abstract val messageTextView: TextView
    protected abstract val loadingProgressBar: ProgressBar
    protected abstract val contentView: View
    protected open val topLoadingIndicator: View? = null

    protected open fun showMessage(text: String) {
        messageTextView.text = text

        messageTextView.visibility = View.VISIBLE
        loadingProgressBar.visibility = View.GONE
        contentView.visibility = View.GONE
        hideTopLoadingIndicator()
    }

    protected open fun showProgressBar() {
        messageTextView.visibility = View.GONE
        loadingProgressBar.visibility = View.VISIBLE
        contentView.visibility = View.GONE
        hideTopLoadingIndicator()

    }

    protected open fun showContent() {
        messageTextView.visibility = View.GONE
        loadingProgressBar.visibility = View.GONE
        contentView.visibility = View.VISIBLE
    }

    protected fun showTopLoadingIndicator() {
        topLoadingIndicator?.visibility = View.VISIBLE
    }

    protected fun hideTopLoadingIndicator() {
        topLoadingIndicator?.visibility = View.GONE
    }
}