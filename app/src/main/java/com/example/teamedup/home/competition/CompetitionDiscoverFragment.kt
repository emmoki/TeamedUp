package com.example.teamedup.home.competition

import android.content.ContentValues
import android.content.ContentValues.TAG
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
import com.example.teamedup.databinding.FragmentCompetionDiscoverBinding
import com.example.teamedup.home.HomeFragmentDirections
import com.example.teamedup.home.SharedViewModel
import com.example.teamedup.repository.model.Tournament
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.TAG
import com.example.teamedup.util.TournamentRecyclerViewClickListener
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class CompetitionDiscoverFragment : Fragment(), TournamentRecyclerViewClickListener{
    private lateinit var _binding : FragmentCompetionDiscoverBinding
    private val binding get() = _binding
    private lateinit var competitionAdapter : CompetitionAdapter
    private val sharedViewModel : SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCompetionDiscoverBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.game.observe(viewLifecycleOwner){game ->
            getData(game)
        }
        setUpCompetitionListView()
    }

    private fun setUpCompetitionListView(){
        binding.rvCompetitionList.apply {
            competitionAdapter = CompetitionAdapter()
            adapter = competitionAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false)
            competitionAdapter.tournamentListener = this@CompetitionDiscoverFragment
        }
        Log.d(ContentValues.TAG, "setupRecyclerView: ${competitionAdapter.tournaments}")
    }

    private fun getData(game : String){
        lifecycleScope.launch {
            binding.pbCompetitionList.isVisible = true
            val response = try {
                RetrofitInstances.api.getTournament(game)
            } catch (e : IOException){
                Log.d("CompetitionDiscover", "$e")
                return@launch
            } catch (e : HttpException){
                Log.d("CompetitionDiscover", "Internal Error")
                return@launch
            }
            if(response.isSuccessful && response.body() != null){
                competitionAdapter.tournaments = response.body()!!
            }else{
                Log.d("CompetitionDiscover", "Response no successful")
            }
            binding.pbCompetitionList.isVisible = false
        }
    }

    override fun onItemClicked(view: View, tournament: Tournament) {
        val direction = HomeFragmentDirections.actionHomeFragmentToTournamentDetailFragment(sharedViewModel.game.value!!, tournament.id)
        findNavController().navigate(direction)
    }

}