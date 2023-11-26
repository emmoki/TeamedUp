package com.example.teamedup.home.competition.competitionDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentTournamentDetailBinding
import com.example.teamedup.repository.model.Tournament
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.TAG
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class TournamentDetailFragment : Fragment() {

    lateinit var _binding : FragmentTournamentDetailBinding
    private val binding get() = _binding
    private val viewmodel : TournamentDetailViewmodel by viewModels()
    private val tournamentDetailFragmentArgs : TournamentDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTournamentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData(tournamentDetailFragmentArgs.gameID, tournamentDetailFragmentArgs.tournamentID)

    }

    private fun getData(gameID : String, tournamentID : String){
        lifecycleScope.launch {
            binding.pbCompetitionDetail.isVisible = true
            val response = try {
                Log.d(TAG, "onViewCreated: Game : ${tournamentDetailFragmentArgs.gameID}")
                Log.d(TAG, "onViewCreated: Tournament : ${tournamentDetailFragmentArgs.tournamentID}")
                RetrofitInstances.api.getTournamentDetail(gameID, tournamentID)
            } catch (e : IOException){
                Log.d(TAG, "$e")
                return@launch
            } catch (e : HttpException){
                Log.d(TAG, "Internal Error")
                return@launch
            }
            if(response.isSuccessful && response.body() != null){
                viewmodel.tournament = response.body()!!
                Log.d(TAG, "getData: ${viewmodel.tournament}")
                with(binding){
                    tvTournamentName.text = viewmodel.tournament.name
                    tvTournamentSummaryType.text = viewmodel.tournament.type
                    tvTournamentSummaryScenes.text = viewmodel.tournament.tier
                    tvTournamentSummaryPrizepool.text = viewmodel.tournament.prize.toString()
                    tvTournamentSummaryQuota.text = viewmodel.tournament.totalParticipant.toString()

                    tvTournamentGameName.text= viewmodel.tournament.game.name
//            tvTournamentGameYear.text = viewmodel.tournament.game.year
//            tvTournamentGameDesc.translationX = viewmodel.tournament.game.desc

                    tvTournamentMoreInformationLocation.text = viewmodel.tournament.location
                    tvTournamentMoreInformationScene.text = viewmodel.tournament.tier
                    tvTournamentMoreInformationQuota.text = viewmodel.tournament.totalParticipant.toString()
                    tvTournamentMoreInformationEntryPrice.text = viewmodel.tournament.fee.toString()
                    tvTournamentMoreInformationMemberRequirement.text = viewmodel.tournament.maxPlayerInTeam.toString()
                    tvTournamentMoreInformationType.text = viewmodel.tournament.type
                    tvTournamentMoreInformationPrizepool.text = viewmodel.tournament.prize.toString()
                }
            }else{
                Log.d(TAG, "Response no successful")
            }
            binding.pbCompetitionDetail.isVisible = false
        }
    }
}