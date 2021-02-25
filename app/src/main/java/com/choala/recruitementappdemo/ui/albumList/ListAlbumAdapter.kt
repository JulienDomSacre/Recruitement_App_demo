package com.choala.recruitementappdemo.ui.albumList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.choala.recruitementappdemo.R
import com.choala.recruitementappdemo.domain.album.AlbumModel
import kotlinx.android.synthetic.main.item_album.view.*

class ListAlbumAdapter(val onClick: (AlbumModel) -> Unit) :
    RecyclerView.Adapter<ListAlbumAdapter.ViewHolder>() {

    private var values: MutableList<AlbumModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_album, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.itemName.text = item.title
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int = values.size

    fun updateValues(items: List<AlbumModel>) {
        values = items.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.tv_albumList_title
    }
}