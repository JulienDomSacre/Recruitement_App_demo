package com.choala.recruitementappdemo.ui.photoList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.choala.recruitementappdemo.R
import com.choala.recruitementappdemo.domain.photo.PhotoModel
import kotlinx.android.synthetic.main.item_photo.view.*

class ListPhotoAdapter : RecyclerView.Adapter<ListPhotoAdapter.ViewHolder>() {

    private var values: MutableList<PhotoModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.itemImage.load(item.url) {
            placeholder(R.drawable.alert)
        }
    }

    override fun getItemCount(): Int = values.size

    fun updateValues(items: List<PhotoModel>) {
        values = items.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.iv_photoList
    }
}