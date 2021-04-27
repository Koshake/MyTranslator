package com.koshake1.mytranslator.view.base

import android.view.View
import androidx.fragment.app.Fragment
import com.koshake1.mytranslator.R
import com.koshake1.mytranslator.model.data.AppState
import com.koshake1.mytranslator.model.data.DataModel
import com.koshake1.mytranslator.utils.ui.AlertDialogFragment
import com.koshake1.mytranslator.view.main.MainActivity
import com.koshake1.mytranslator.viewmodel.BaseViewModel
import com.koshake1.mytranslator.viewmodel.Interactor
import kotlinx.android.synthetic.main.loading_layout.*
import kotlinx.android.synthetic.main.loading_layout.progress_bar_horizontal
import kotlinx.android.synthetic.main.loading_layout.progress_bar_round

private const val DIALOG_FRAGMENT_TAG = "com.koshake1.mytranslator.view.base"

abstract class BaseFragment<T : AppState, I : Interactor<T>> : Fragment(), MainView {

    override  fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                showViewWorking()
                appState.dataModel?.let {
                    if (it.isEmpty()) {
                        showAlertDialog(
                            getString(R.string.dialog_tittle_sorry),
                            getString(R.string.empty_server_response_on_success)
                        )
                    } else {
                        setDataToAdapter(it)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    progress_bar_horizontal.visibility = View.VISIBLE
                    progress_bar_round.visibility = View.GONE
                    progress_bar_horizontal.progress = appState.progress
                } else {
                    progress_bar_horizontal.visibility = View.GONE
                    progress_bar_round.visibility = View.VISIBLE
                }
            }
            is AppState.Error -> {
                showViewWorking()
                showAlertDialog(getString(R.string.error_stub), appState.error.message)
            }
        }
    }

    abstract val model : BaseViewModel<T>

    abstract fun setDataToAdapter(data: List<DataModel>)

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    protected fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message).show((activity as MainActivity)?.supportFragmentManager, DIALOG_FRAGMENT_TAG)
    }


    private fun showViewWorking() {
        loading_frame_layout.visibility = View.GONE
    }

    private fun showViewLoading() {
        loading_frame_layout.visibility = View.VISIBLE
    }

    private fun isDialogNull(): Boolean {
        return (activity as MainActivity)?.supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }

}