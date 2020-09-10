package com.example.callofdutymw_stats.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.callofdutymw_stats.R
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer
import kotlinx.android.synthetic.main.recycler_view_favorite_user.view.*

class RecyclerAdapterFavoriteUser : RecyclerView.Adapter<RecyclerAdapterFavoriteUser.ViewHolder>() {

    companion object {
        private val listOfFavoriteUser = ArrayList<UserInformationMultiplayer>()

        fun addUserToFavorites(user: UserInformationMultiplayer) {
            listOfFavoriteUser.add(user)
            Log.d("User added ", user.userNickname)
        }

        fun removeUserToFavorites(user: UserInformationMultiplayer) {
            listOfFavoriteUser.remove(user)
            Log.d("User removed ", user.userNickname)
        }

        fun getListOfFavoriteUser(): List<UserInformationMultiplayer> {
            return listOfFavoriteUser
        }
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
            onClickListener.onClick(position)
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