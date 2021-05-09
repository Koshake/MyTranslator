package com.koshake1.mytranslator.view.descriptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.koshake1.mytranslator.R
import com.koshake1.mytranslator.view.main.MainActivity
import com.koshake1.utils.network.isOnline
import com.koshake1.utils.ui.AlertDialogFragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_descriptions.*

class DescriptionsFragment : Fragment() {

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

        description_screen_swipe_refresh_layout.setOnRefreshListener { startLoadingOrShowError() }
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
        description_header.text = bundle?.getString(WORD_EXTRA)
        description_textview.text = bundle?.getString(DESCRIPTION_EXTRA)
        val imageLink = bundle?.getString(URL_EXTRA)
        if (imageLink.isNullOrBlank()) {
            stopRefreshAnimationIfNeeded()
        } else {
            usePicassoToLoadPhoto(description_imageview, imageLink)
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
        if (isOnline(requireContext())) {
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
        }
    }

    private fun stopRefreshAnimationIfNeeded() {
        if (description_screen_swipe_refresh_layout.isRefreshing) {
            description_screen_swipe_refresh_layout.isRefreshing = false
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