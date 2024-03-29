package com.example.teamedup.home.tournament

import android.content.ContentValues
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
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.TournamentRecyclerViewClickListener
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class TournamentDiscoverFragment : Fragment(), TournamentRecyclerViewClickListener{
    private lateinit var _binding : FragmentCompetionDiscoverBinding
    private val binding get() = _binding
    private lateinit var tournamentAdapter : TournamentAdapter
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
            setUpCompetitionRecyclerView()
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setTab("Tournament")
    }

    private fun setUpCompetitionRecyclerView(){
        binding.rvCompetitionList.apply {
            tournamentAdapter = TournamentAdapter()
            adapter = tournamentAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false)
            tournamentAdapter.tournamentListener = this@TournamentDiscoverFragment
        }
        Log.d(ContentValues.TAG, "setupRecyclerView: ${tournamentAdapter.tournaments}")
    }

    private fun getData(game : String){
        lifecycleScope.launch {
            binding.pbCompetitionList.isVisible = true
            val response = try {
                RetrofitInstances.api.getTournament(GlobalConstant.ATHENTICATION_TOKEN, game)
            } catch (e : IOException){
                Log.d("CompetitionDiscover", "$e")
                return@launch
            } catch (e : HttpException){
                Log.d("CompetitionDiscover", "Internal Error")
                return@launch
            }
            if(response.isSuccessful && response.body() != null){
                tournamentAdapter.tournaments = response.body()!!.data
            }else{
                Log.d("CompetitionDiscover", "Response no successful")
            }
            binding.pbCompetitionList.isVisible = false
        }
    }

    override fun onItemClicked(view: View, tournament: Tournament) {
        val direction = HomeFragmentDirections.actionHomeFragmentToTournamentDetailFragment(sharedViewModel.game.value!!, tournament.id!!)
        findNavController().navigate(direction)
    }

}