package com.example.teamedup.util

import android.view.View
import com.example.teamedup.repository.model.Forum
import com.example.teamedup.repository.model.Tournament

interface ForumRecyclerViewClickListener {
    fun onItemClicked(view : View, forum: Forum)
}