package com.example.callofdutymw_stats.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.callofdutymw_stats.R
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer
import kotlinx.android.synthetic.main.recycler_view_favorite_user.view.*

class RecyclerAdapterFavoriteUser(private val listOfFavoriteUser: ArrayList<UserInformationMultiplayer>) :
    RecyclerView.Adapter<RecyclerAdapterFavoriteUser.ViewHolder>() {

    fun addUserToFavorites(user: UserInformationMultiplayer) {
        listOfFavoriteUser.add(user)
    }

    fun deleteUserInFavorites(user: UserInformationMultiplayer) {
        listOfFavoriteUser.remove(user)
    }

    fun getListOfFavoriteUser(): List<UserInformationMultiplayer> {
        return listOfFavoriteUser
    }

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
        holder.bindItems(listOfFavoriteUser[position])
        deleteIconOnClick(holder, position)
    }

    private fun deleteIconOnClick(holder: RecyclerAdapterFavoriteUser.ViewHolder, position: Int) {
        holder.itemView.imageViewDeleteFavoriteUser.setOnClickListener {
            //if (onClickListener != null) onClickListener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return listOfFavoriteUser.size
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