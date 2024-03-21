package com.example.teamedup.util

import DialogBuilder
import androidx.fragment.app.FragmentManager

class DialogExtension{
    fun successCreateComment(supportFragmentManager : FragmentManager){
        val builder = DialogBuilder(supportFragmentManager)
            .setTitle("Comment Created")
            .setMessage("Your comment has been sent")
            .build()
        return builder
    }

    fun successCreateForum(supportFragmentManager: FragmentManager){
        val builder = DialogBuilder(supportFragmentManager)
            .setTitle("Forum Created")
            .setMessage("Your forum has been created")
            .build()
        return builder
    }

    fun successCreateTournament(supportFragmentManager: FragmentManager){
        val builder = DialogBuilder(supportFragmentManager)
            .setTitle("Tournament Created")
            .setMessage("Tournament has been created!")
            .setSecondMessageVisible(true)
            .build()
        return builder
    }
}