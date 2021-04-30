package com.koshake1.mytranslator.view.history.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.koshake1.model.data.DataModel
import com.koshake1.mytranslator.R
import kotlinx.android.synthetic.main.history_item.view.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var data : List<DataModel> = arrayListOf()

    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.history_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class HistoryViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        fun bind(dataModel: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.header_history_item.text = dataModel.text
                itemView.setOnClickListener {
                    Toast.makeText(itemView.context, "on click: ${dataModel.text}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}