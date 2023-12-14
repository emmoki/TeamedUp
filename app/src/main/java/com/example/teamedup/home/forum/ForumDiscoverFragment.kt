package com.example.teamedup.home.forum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamedup.databinding.FragmentForumDiscoverBinding
import com.example.teamedup.home.HomeFragmentDirections
import com.example.teamedup.home.SharedViewModel
import com.example.teamedup.repository.model.Forum
import com.example.teamedup.repository.model.Game
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.ForumRecyclerViewClickListener
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.TAG
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ForumDiscoverFragment : Fragment(), ForumRecyclerViewClickListener {
    private lateinit var _binding : FragmentForumDiscoverBinding
    private val binding get()  = _binding
    private lateinit var forumAdapter : ForumAdapter
    private val sharedViewModel : SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForumDiscoverBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: Called")
        sharedViewModel.game.observe(viewLifecycleOwner){game ->
            getData(game)
            setUpForumRecyclerView()
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setTab("Forum")

    }

    private fun getData(game : String){
        lifecycleScope.launch {
            val response = try {
                RetrofitInstances.api.getForums(GlobalConstant.ATHENTICATION_TOKEN,game)
            } catch (e : IOException){
                Log.d("CompetitionDiscover", "$e")
                return@launch
            } catch (e : HttpException){
                Log.d("CompetitionDiscover", "Internal Error")
                return@launch
            }
            if(response.isSuccessful && response.body() != null){
                forumAdapter.forums = response.body()!!.data
            }else{
                Log.d("CompetitionDiscover", "Response no successful")
            }
        }
    }

    private fun setUpForumRecyclerView(){
        binding.rvForumList.apply {
            forumAdapter = ForumAdapter()
            adapter = forumAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false)
            forumAdapter.forumListener = this@ForumDiscoverFragment
        }
    }

    override fun onItemClicked(view: View, forum: Forum) {
        Log.d(TAG, "onItemClicked: ${forum.id}")
        val direction = HomeFragmentDirections.actionHomeFragmentToFragmentDetailFragment(sharedViewModel.game.value!!, forum.id!!)
        findNavController().navigate(direction)
    }
}