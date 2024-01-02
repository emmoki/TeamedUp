package com.example.teamedup.util

import android.view.View
import com.example.teamedup.repository.model.User

interface UserRecyclerViewClickListener {
    fun onItemClicked(view : View, user: User)
}