package com.example.teamedup.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentHomeBinding
import com.example.teamedup.repository.model.Game
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.GameRecyclerViewClickListener
import com.example.teamedup.util.TAG
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class HomeFragment : Fragment(), GameRecyclerViewClickListener {
    private lateinit var _binding : FragmentHomeBinding
    private val binding get() = _binding
    private lateinit var gameAdapter: GameAdapter
    private lateinit var viewPagerAdapter: ContentViewPagerAdapter
    private val sharedViewModel : SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getData()
        setupViewPager()
    }

    private fun setupViewPager(){
        viewPagerAdapter = ContentViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        with(binding){
            vpContent.adapter = viewPagerAdapter

            TabLayoutMediator(tlContentDivider, vpContent){ tab, position ->
                when(position){
                    0 -> tab.text = getString(R.string.competition_tab)
                    1 -> tab.text = getString(R.string.forum_tab)
                }
            }.attach()
        }
    }

    private fun setupRecyclerView(){
        binding.rvGameList.apply {
            gameAdapter = GameAdapter()
            adapter = gameAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            gameAdapter.gameListener = this@HomeFragment
        }
    }

    private fun getData(){
        lifecycleScope.launch {
            binding.pbGameList.isVisible = true
            val response = try {
                RetrofitInstances.api.getGame()
            } catch (e : IOException){
                Log.d("HomeFragment", "$e")
                return@launch
            } catch (e : HttpException){
                Log.d("HomeFragment", "Internal Error")
                return@launch
            }
            if(response.isSuccessful && response.body() != null){
                gameAdapter.games = response.body()!!
                Log.d(TAG, "setupRecyclerView: ${gameAdapter.games}")
            }else{
                Log.d("HomeFragment", "Response no successful")
            }
            binding.pbGameList.isVisible = false
        }
    }

    override fun onItemClicked(view: View, game: Game) {
        Log.d("HomeFragment", "onItemClicked: name: ${game.name} id: ${game.id}")
        sharedViewModel.setGame(game.id)
    }
}