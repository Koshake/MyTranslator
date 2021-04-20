package com.koshake1.mytranslator.view.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.koshake1.mytranslator.R
import com.koshake1.mytranslator.utils.getEmptyString
import kotlinx.android.synthetic.main.search_dialog_fragment.*

class SearchDialogFragment : BottomSheetDialogFragment() {

    private lateinit var searchEditText: TextInputEditText
    private lateinit var clearTextImageView: ImageView
    private lateinit var searchButton: TextView
    private var onSearchClickListener: OnSearchClickListener? = null

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (searchEditText.text != null && !searchEditText.text.toString().isEmpty()) {
                searchButton.isEnabled = true
                clearTextImageView.visibility = View.VISIBLE
            } else {
                searchButton.isEnabled = false
                clearTextImageView.visibility = View.GONE
            }
        }

        override fun afterTextChanged(p0: Editable?) { }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchEditText = search_edit_text
        clearTextImageView = clear_text_imageview
        searchButton = search_button_textview

        searchButton.setOnClickListener {
            onSearchClickListener?.onClick(searchEditText.text.toString())
            dismiss()
        }

        searchEditText.addTextChangedListener(textWatcher)

        clearTextImageView.setOnClickListener {
            searchEditText.setText(String.getEmptyString())
            searchButton.isEnabled = false
        }
    }

    override fun onDestroy() {
        onSearchClickListener = null
        super.onDestroy()
    }

    internal fun setOnSearchClickListener(listener: OnSearchClickListener) {
        onSearchClickListener = listener
    }

    interface OnSearchClickListener {
        fun onClick(searchWord: String)
    }

    companion object {
        fun newInstance() : SearchDialogFragment = SearchDialogFragment()
    }
}