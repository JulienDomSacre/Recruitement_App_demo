package com.choala.recruitementappdemo.ui.userList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.choala.recruitementappdemo.R
import com.choala.recruitementappdemo.domain.user.UserModel
import kotlinx.android.synthetic.main.item_user.view.*

class ListUserAdapter(val onClick: (UserModel) -> Unit) :
    RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    private var values: MutableList<UserModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.itemName.text = item.name
        holder.itemLastName.text = item.username
        holder.itemMail.text = item.email
        holder.itemPhone.text = item.phone
        holder.itemWebsite.text = item.website
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int = values.size

    fun updateValues(items: List<UserModel>) {
        values = items.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.tv_userItem_name
        val itemLastName: TextView = view.tv_userItem_lastName
        val itemMail: TextView = view.tv_userItem_mail
        val itemPhone: TextView = view.tv_userItem_phone
        val itemWebsite: TextView = view.tv_userItem_webSite
    }
}