package com.koshake1.mytranslator.view.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.koshake1.core.base.BaseFragment
import com.koshake1.model.data.AppState
import com.koshake1.model.data.DataModel
import com.koshake1.mytranslator.R
import com.koshake1.mytranslator.utils.convertMeaningsToString
import com.koshake1.mytranslator.view.descriptions.DescriptionsFragment
import com.koshake1.mytranslator.view.history.HistoryFragment
import com.koshake1.mytranslator.view.main.adapter.MainAdapter
import com.koshake1.mytranslator.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<AppState, MainInteractor>() {

    companion object {
        const val TAG = "fragment tag"
        const val SEARCH_FRAGMENT_TAG: String = "add_search_dialog_fragment"
    }

    override val model by viewModel<MainViewModel>()

    private val observer = Observer<AppState> { renderData(it) }
    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                (requireActivity() as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.container,
                        DescriptionsFragment.create(
                            data.text!!,
                            convertMeaningsToString(data.meanings!!),
                            data.meanings!![0].imageUrl
                        )
                    )
                    .addToBackStack("myTranslator")
                    .commit()
            }
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.history_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                (requireActivity() as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.container, HistoryFragment())
                    .addToBackStack("myTranslator")
                    .commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "Main fragment onCreate view ")
        model.viewState.observe(this@MainFragment, observer)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "Main fragment onViewCreated ")
        main_activity_recyclerview.layoutManager = LinearLayoutManager(context)
        main_activity_recyclerview.adapter = adapter

        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)

        search_fab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    model.getData(searchWord, true)
                }
            })

            searchDialogFragment.show(
                (activity as AppCompatActivity).supportFragmentManager,
                SEARCH_FRAGMENT_TAG
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Main fragment onDestroy ")
    }
}