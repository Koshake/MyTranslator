package com.koshake1.mytranslator.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koshake1.model.data.DataModel
import com.koshake1.mytranslator.R
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.main_recyclerview_item.view.*

class MainAdapter(
    private var onListItemClickListener: OnListItemClickListener
) :
    RecyclerView.Adapter<MainAdapter.MainAdapterViewHolder>() {

    private var data : List<DataModel> = arrayListOf()

    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapterViewHolder {
        return MainAdapterViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_recyclerview_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: MainAdapterViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MainAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.header_textview_recycler_item.text = data.text
                itemView.description_textview_recycler_item.text =
                    data.meanings?.get(0)?.translation?.text

                itemView.setOnClickListener { onListItemClickListener.onItemClick(data) }
            }
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(data: DataModel)
    }
}