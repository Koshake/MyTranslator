package com.koshake1.mytranslator.view.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.koshake1.core.base.BaseFragment
import com.koshake1.model.data.AppState
import com.koshake1.model.data.DataModel
import com.koshake1.mytranslator.R
import com.koshake1.mytranslator.view.history.adapter.HistoryAdapter
import com.koshake1.mytranslator.view.main.MainActivity
import com.koshake1.mytranslator.viewmodel.HistoryViewModel
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.android.viewmodel.ext.android.viewModel

class HistoryFragment : BaseFragment<AppState, HistoryInteractor>() {

    companion object {
        private val TAG = "history"
    }

    override val model by viewModel<HistoryViewModel>()

    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "History fragment onCreate view ")
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "History fragment onViewCreated ")
        history_activity_recyclerview.layoutManager = LinearLayoutManager(context)
        history_activity_recyclerview.adapter = adapter

        setHasOptionsMenu(true)
        (requireActivity() as MainActivity)?.supportActionBar?.setHomeButtonEnabled(true)
        (requireActivity() as MainActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        model.viewState.observe(this@HistoryFragment, Observer<AppState> { renderData(it) })

        model.getData("", false)
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

    override fun setDataToAdapter(data: List<DataModel>) {
        Log.d(TAG, "Set data to adapter ")
        adapter.setData(data)
    }
}