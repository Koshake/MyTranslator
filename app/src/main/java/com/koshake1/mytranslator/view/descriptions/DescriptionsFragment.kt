package com.koshake1.mytranslator.view.descriptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.koshake1.mytranslator.R
import com.koshake1.mytranslator.view.main.MainActivity
import com.koshake1.utils.network.OnlineLiveData
import com.koshake1.utils.ui.AlertDialogFragment
import com.koshake1.utils.ui.viewById
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class DescriptionsFragment : Fragment() {

    private val descriptionSwipeLayout by viewById<SwipeRefreshLayout>(R.id.description_screen_swipe_refresh_layout)
    private val descriptionHeader by viewById<TextView>(R.id.description_header)
    private val descriptionText by viewById<TextView>(R.id.description_textview)
    private val descriptionImage by viewById<ImageView>(R.id.description_imageview)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_descriptions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (requireActivity() as MainActivity)?.supportActionBar?.setHomeButtonEnabled(true)
        (requireActivity() as MainActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        descriptionSwipeLayout.setOnRefreshListener { startLoadingOrShowError() }
        setData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                (requireActivity() as MainActivity)?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setData() {
        val bundle = arguments
        descriptionHeader.text = bundle?.getString(WORD_EXTRA)
        descriptionText.text = bundle?.getString(DESCRIPTION_EXTRA)
        val imageLink = bundle?.getString(URL_EXTRA)
        if (imageLink.isNullOrBlank()) {
            stopRefreshAnimationIfNeeded()
        } else {
            usePicassoToLoadPhoto(descriptionImage, imageLink)
        }
    }

    private fun usePicassoToLoadPhoto(imageView: ImageView, imageLink: String) {
        Picasso.with(requireContext()).load("https:$imageLink")
            .placeholder(R.drawable.ic_no_photo_vector).fit().centerCrop()
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    stopRefreshAnimationIfNeeded()
                }

                override fun onError() {
                    stopRefreshAnimationIfNeeded()
                    imageView.setImageResource(R.drawable.ic_load_error_vector)
                }
            })
    }

    private fun startLoadingOrShowError() {
        context?.let {
            OnlineLiveData(it).observe(
                this@DescriptionsFragment,
                Observer<Boolean>{ isOnline->
                    if (isOnline) {
                    setData()
                } else {
                    AlertDialogFragment.newInstance(
                        getString(R.string.dialog_title_device_is_offline),
                        getString(R.string.dialog_message_device_is_offline)
                    ).show(
                        requireFragmentManager(),
                        DIALOG_FRAGMENT_TAG
                    )
                    stopRefreshAnimationIfNeeded()
                } }
            )
        }
    }

    private fun stopRefreshAnimationIfNeeded() {
        if (descriptionSwipeLayout.isRefreshing) {
            descriptionSwipeLayout.isRefreshing = false
        }
    }

    companion object {
        private const val DIALOG_FRAGMENT_TAG =
            "com.koshake1.mytranslator.view.descriptions.DescriptionsFragment.dialog"
        private const val WORD_EXTRA =
            "com.koshake1.mytranslator.view.descriptions.DescriptionsFragment.word"
        private const val DESCRIPTION_EXTRA =
            "com.koshake1.mytranslator.view.descriptions.DescriptionsFragment.description"
        private const val URL_EXTRA =
            "com.koshake1.mytranslator.view.descriptions.DescriptionsFragment.url"

        fun create(
            word: String,
            description: String,
            url: String?
        ): DescriptionsFragment {

            val descriptionsFragment = DescriptionsFragment()
            val args = Bundle()
            args.putString(WORD_EXTRA, word)
            args.putString(DESCRIPTION_EXTRA, description)
            args.putString(URL_EXTRA, url)

            descriptionsFragment.arguments = args

            return descriptionsFragment
        }
    }
}