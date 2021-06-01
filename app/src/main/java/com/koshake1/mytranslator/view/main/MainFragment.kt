package com.koshake1.mytranslator.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.koshake1.core.base.BaseFragment
import com.koshake1.model.data.AppState
import com.koshake1.model.data.DataModel
import com.koshake1.mytranslator.R
import com.koshake1.mytranslator.di.injectDependencies
import com.koshake1.mytranslator.utils.convertMeaningsToString
import com.koshake1.mytranslator.view.descriptions.DescriptionsFragment
import com.koshake1.mytranslator.view.main.adapter.MainAdapter
import com.koshake1.mytranslator.viewmodel.MainViewModel
import com.koshake1.utils.ui.viewById
import org.koin.android.scope.currentScope
import android.provider.Settings

class MainFragment : BaseFragment<AppState, MainInteractor>() {

    companion object {
        const val TAG = "fragment tag"
        const val SEARCH_FRAGMENT_TAG: String = "add_search_dialog_fragment"
        private const val HISTORY_FRAGMENT_PATH = "com.koshake1.historyscreen.history.HistoryActivity"
        private const val HISTORY_FRAGMENT_FEATURE_NAME = "historyscreen"
        const val SETTINGS_MENU_TEG = 42
    }

    private lateinit var splitInstallManager: SplitInstallManager

    override lateinit var model: MainViewModel

    private val mainActivityRecyclerView by viewById<RecyclerView>(R.id.main_activity_recyclerview)
    private val searchFAB by viewById<FloatingActionButton>(R.id.search_fab)

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
        inflater.inflate(R.menu.menu_screen_settings, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                splitInstallManager = SplitInstallManagerFactory.create(activity?.applicationContext)
                val request =
                    SplitInstallRequest
                        .newBuilder()
                        .addModule(HISTORY_FRAGMENT_FEATURE_NAME)
                        .build()
                splitInstallManager
                    .startInstall(request)
                    .addOnSuccessListener {
                        val intent = Intent().setClassName(activity?.packageName!!, HISTORY_FRAGMENT_PATH)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            activity?.applicationContext,
                            "Couldn't download feature: " + it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                true
            }
            R.id.menu_screen_settings -> {
                startActivityForResult(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY), SETTINGS_MENU_TEG)
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
        injectDependencies()
        initViewModel()
       // model.viewState.observe(this@MainFragment, observer)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "Main fragment onViewCreated ")
        check(mainActivityRecyclerView.adapter == null) { "The ViewModel should be initialised first" }
        mainActivityRecyclerView.layoutManager = LinearLayoutManager(context)
        mainActivityRecyclerView.adapter = adapter

        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)

        searchFAB.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    if (isNetworkAvailable) {
                        model.getData(searchWord, isNetworkAvailable)
                    } else {
                        showNoInternetConnectionDialog()
                    }
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

    private fun initViewModel() {
        val viewModel: MainViewModel by currentScope.inject()
        model = viewModel
        model.viewState.observe(this@MainFragment, observer)
    }
}