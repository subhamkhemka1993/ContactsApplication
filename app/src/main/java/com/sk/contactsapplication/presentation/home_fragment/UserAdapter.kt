package com.sk.contactsapplication.presentation.home_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sk.contactsapplication.R
import com.sk.contactsapplication.data.response_models.UserInfo
import com.sk.contactsapplication.databinding.ItemUserBinding

class UserAdapter(private val onItemSelected: (Int) -> Unit) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val listOfUsers = arrayListOf<UserInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = listOfUsers.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userInfo = listOfUsers[position]
        val color = if (position % 2 == 0) {
            R.color.color_F3F3F3
        } else {
            R.color.white
        }
        holder.setData(userInfo, color)
    }

    fun setData(userList: List<UserInfo>) {
        val diffCallback = UserDiffUtil(listOfUsers, userList)
        val diffUsers = DiffUtil.calculateDiff(diffCallback)
        listOfUsers.clear()
        listOfUsers.addAll(userList)
        diffUsers.dispatchUpdatesTo(this)
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemSelected(adapterPosition)
            }
        }

        fun setData(userInfo: UserInfo, color: Int) {
            binding.apply {
                tvName.text = userInfo.name
                tvEmail.text = userInfo.email
                flImageFrame.isVisible = userInfo.isSelected.not()
                Glide.with(binding.root.context).load(userInfo.avatar).centerCrop().into(ivAvatar)
                val bgColor = if (userInfo.isSelected) {
                    R.color.color_C6A2E8
                } else {
                    color
                }
                root.setBackgroundColor(ContextCompat.getColor(binding.root.context, bgColor))
                tvName.setTextColor(ContextCompat.getColor(binding.root.context, if (userInfo.isSelected) {
                    R.color.white
                } else {
                    R.color.black
                }))
                tvEmail.setTextColor(ContextCompat.getColor(binding.root.context, if (userInfo.isSelected) {
                    R.color.white
                } else {
                    android.R.color.darker_gray
                }))
            }
        }

    }
}