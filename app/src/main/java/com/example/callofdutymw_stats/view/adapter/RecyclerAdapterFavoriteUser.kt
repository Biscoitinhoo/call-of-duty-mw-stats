package com.example.callofdutymw_stats.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.callofdutymw_stats.R
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer
import com.example.callofdutymw_stats.viewmodel.UserInformationViewModel
import kotlinx.android.synthetic.main.recycler_view_favorite_user.view.*

class RecyclerAdapterFavoriteUser(context: Context) :
    RecyclerView.Adapter<RecyclerAdapterFavoriteUser.ViewHolder>() {

    private val userInformationViewModel = UserInformationViewModel(context)
    private lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapterFavoriteUser.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_favorite_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterFavoriteUser.ViewHolder, position: Int) {
        holder.bindItems(userInformationViewModel.getAllFavoriteUsers()[position])
        deleteIconOnClick(holder, position)
    }

    private fun deleteIconOnClick(holder: RecyclerAdapterFavoriteUser.ViewHolder, position: Int) {
        holder.itemView.imageViewDeleteFavoriteUser.setOnClickListener {
            //if (onClickListener != null) onClickListener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return userInformationViewModel.getAllFavoriteUsers().size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(userInformationMultiplayer: UserInformationMultiplayer) {
            itemView.textViewUserLevel.text = userInformationMultiplayer.level.toString()
            itemView.textViewUsername.text = userInformationMultiplayer.userNickname
        }
    }

    public interface OnClickListener {
        fun onClick(position: Int)
    }
}