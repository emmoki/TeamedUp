package com.example.teamedup.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.teamedup.home.tournament.TournamentDiscoverFragment
import com.example.teamedup.home.forum.ForumDiscoverFragment

class ContentViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when(position){
            0 -> fragment = TournamentDiscoverFragment()
            1 -> fragment = ForumDiscoverFragment()
        }
        return fragment
    }

}