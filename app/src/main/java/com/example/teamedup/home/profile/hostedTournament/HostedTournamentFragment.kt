package com.example.teamedup.home.profile.participatedTournament

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamedup.databinding.FragmentHostedTournamentBinding
import com.example.teamedup.home.HomeFragmentDirections
import com.example.teamedup.home.SharedViewModel
import com.example.teamedup.home.competition.CompetitionAdapter
import com.example.teamedup.repository.model.Tournament
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.TAG
import com.example.teamedup.util.TournamentRecyclerViewClickListener
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class HostedTournamentFragment : Fragment(), TournamentRecyclerViewClickListener {
    private lateinit var _binding : FragmentHostedTournamentBinding
    private val binding get() = _binding
    private lateinit var hostedTournamentAdapter : CompetitionAdapter
    private val sharedViewModel : SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHostedTournamentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHostedTournamentList()
        setupToolbar()
        getData()
    }

    private fun getData(){
        lifecycleScope.launch {
//            binding.pbCompetitionList.isVisible = true
            val response = try {
                RetrofitInstances.api.getUser(GlobalConstant.ATHENTICATION_TOKEN)
            } catch (e : IOException){
                Log.d("CompetitionDiscover", "$e")
                return@launch
            } catch (e : HttpException){
                Log.d("CompetitionDiscover", "Internal Error")
                return@launch
            }
            if(response.isSuccessful && response.body() != null){
                hostedTournamentAdapter.tournaments = response.body()!!.data.hostedTournament
            }else{
                Log.d("CompetitionDiscover", "Response no successful")
            }
//            binding.pbCompetitionList.isVisible = false
        }
    }

    private fun setupHostedTournamentList(){
        binding.rvHostedTournamentList.apply {
            hostedTournamentAdapter = CompetitionAdapter()
            adapter = hostedTournamentAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false)
            hostedTournamentAdapter.tournamentListener = this@HostedTournamentFragment
        }
        Log.d(ContentValues.TAG, "setupRecyclerView: ${hostedTournamentAdapter.tournaments}")
    }

    override fun onItemClicked(view: View, tournament: Tournament) {
        val direction = HostedTournamentFragmentDirections.actionHostedTournamentFragmentToTournamentDetailFragment(tournament.game!!.id, tournament.id!!)
        findNavController().navigate(direction)
    }

    private fun setupToolbar(){
        binding.apply {
            toolbar.tvToolbarTitle.text = "Back"
            toolbar.back.setOnClickListener { findNavController().popBackStack() }
            toolbar.btnCreate.visibility = View.GONE
        }
    }
}