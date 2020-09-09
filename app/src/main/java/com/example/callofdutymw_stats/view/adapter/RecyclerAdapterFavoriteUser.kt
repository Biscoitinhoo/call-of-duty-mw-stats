package com.example.callofdutymw_stats.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.callofdutymw_stats.R
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer

class RecyclerAdapterFavoriteUser : RecyclerView.Adapter<RecyclerAdapterFavoriteUser.ViewHolder>() {

    companion object {
        val listOfFavoriteUser = ArrayList<UserInformationMultiplayer>()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapterFavoriteUser.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_favorite_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterFavoriteUser.ViewHolder, position: Int) {
        holder.bindItems(listOfFavoriteUser[position])
    }

    override fun getItemCount(): Int {
        return listOfFavoriteUser.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(userInformationMultiplayer: UserInformationMultiplayer) {

        }
    }
}