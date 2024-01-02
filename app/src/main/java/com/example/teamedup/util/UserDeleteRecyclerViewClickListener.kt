package com.example.teamedup.util

import android.view.View
import com.example.teamedup.repository.model.User

interface UserDeleteRecyclerViewClickListener {
    fun onUserDeleteClicked(view : View, user: User)
}