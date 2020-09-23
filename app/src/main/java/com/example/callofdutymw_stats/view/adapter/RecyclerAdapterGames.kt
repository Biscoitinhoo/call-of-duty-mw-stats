package com.example.callofdutymw_stats.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.callofdutymw_stats.R
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer
import com.example.callofdutymw_stats.viewmodel.UserInformationViewModel
import kotlinx.android.synthetic.main.recycler_view_favorite_user.view.*

class RecyclerAdapterGames(context: Context) :
    RecyclerView.Adapter<RecyclerAdapterGames.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapterGames.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_games, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterGames.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindItems(userInformationMultiplayer: UserInformationMultiplayer) {

        }
    }
}