package com.sk.contactsapplication.presentation.home

import androidx.recyclerview.widget.DiffUtil
import com.sk.contactsapplication.data.response_models.UserInfo

class UserDiffUtil(private val oldList: List<UserInfo>, private val newList: List<UserInfo>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPostion: Int, newPosition: Int): Boolean {
        val oldUserInfo = oldList[oldItemPostion]
        val newUserInfo = newList[newPosition]
        return oldUserInfo.name == newUserInfo.name && oldUserInfo.email == newUserInfo.email && oldUserInfo.isSelected == newUserInfo.isSelected
    }

}