package com.example.teamedup.home.profile.participatedTournament

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamedup.databinding.FragmentParticipatedTournamentBinding
import com.example.teamedup.home.competition.CompetitionAdapter
import com.example.teamedup.repository.model.Team
import com.example.teamedup.repository.model.Tournament
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.TAG
import com.example.teamedup.util.TournamentRecyclerViewClickListener
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class ParticipatedTournamentFragment : Fragment(), TournamentRecyclerViewClickListener {
    private lateinit var _binding : FragmentParticipatedTournamentBinding
    private val binding get() = _binding
    private lateinit var participatedTournamentAdapter: CompetitionAdapter
    private lateinit var teamlist : List<Team>
    private val viewModel : ParticipatedTournamentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentParticipatedTournamentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupHostedTournamentList()
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
                teamlist = response.body()!!.data.teamList
                Log.d(TAG, "TeamList: $teamlist")
                participatedTournamentAdapter.tournaments = viewModel.getTournamentFromTeam(teamlist)
            }else{
                Log.d("CompetitionDiscover", "Response no successful")
            }
//            binding.pbCompetitionList.isVisible = false
        }
    }

    private fun setupHostedTournamentList(){
        binding.rvTournamentList.apply {
            participatedTournamentAdapter = CompetitionAdapter()
            adapter = participatedTournamentAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false)
            participatedTournamentAdapter.tournamentListener = this@ParticipatedTournamentFragment
        }
        Log.d(ContentValues.TAG, "setupRecyclerView: ${participatedTournamentAdapter.tournaments}")
    }

    override fun onItemClicked(view: View, tournament: Tournament) {
        Log.d(TAG, "onItemClicked: ${tournament.id}")
    }

    private fun setupToolbar(){
        binding.apply {
            toolbar.tvToolbarTitle.text = "Back"
            toolbar.back.setOnClickListener { findNavController().popBackStack() }
            toolbar.btnCreate.visibility = View.GONE
        }
    }

}