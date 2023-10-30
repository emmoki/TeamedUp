package com.example.teamedup.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentHomeBinding
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.log


class HomeFragment : Fragment() {
    private lateinit var _binding : FragmentHomeBinding
    private val binding get() = _binding
    private lateinit var gameAdapter: GameAdapter

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
    }

    private fun setupRecyclerView(){
        binding.rvGameList.apply {
            gameAdapter = GameAdapter()
            adapter = gameAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
        }
        Log.d(TAG, "setupRecyclerView: ${gameAdapter.games}")
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
            }else{
                Log.d("HomeFragment", "Response no successful")
            }
            binding.pbGameList.isVisible = false
        }
    }
}