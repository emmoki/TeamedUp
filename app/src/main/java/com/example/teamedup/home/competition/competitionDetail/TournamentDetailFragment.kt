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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentTournamentDetailBinding
import com.example.teamedup.databinding.MemberListIconItemBinding
import com.example.teamedup.home.competition.createTeam.MemberIconAdapter
import com.example.teamedup.repository.model.Tournament
import com.example.teamedup.repository.model.User
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.TAG
import com.example.teamedup.util.moneySuffix
import com.google.firebase.platforminfo.GlobalLibraryVersionRegistrar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class TournamentDetailFragment : Fragment() {

    lateinit var _binding : FragmentTournamentDetailBinding
    private val binding get() = _binding
    private val viewmodel : TournamentDetailViewmodel by viewModels()
    private val tournamentDetailFragmentArgs : TournamentDetailFragmentArgs by navArgs()
    private lateinit var memberIconAdapter : MemberIconAdapter

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
                RetrofitInstances.api.getTournamentDetail(GlobalConstant.ATHENTICATION_TOKEN,gameID, tournamentID)
            } catch (e : IOException){
                Log.d(TAG, "$e")
                return@launch
            } catch (e : HttpException){
                Log.d(TAG, "Internal Error")
                return@launch
            }
            if(response.isSuccessful && response.body() != null){
                viewmodel.tournament = response.body()!!.data
                Log.d(TAG, "getData: ${viewmodel.tournament}")
                setUpView()
            }else{
                Log.d(TAG, "Response no successful")
            }
            binding.pbCompetitionDetail.isVisible = false
        }
    }

    private fun setUpView(){
        sessionManager(GlobalConstant.user)
        setUpJoinTournamentView()
        setupSummaryTournamentInfo()
        setupDetailTournamentInfo()
        setupTournamentGameView()
    }

    private fun setupSummaryTournamentInfo(){
        binding.apply {
            tvTournamentName.text = viewmodel.tournament.name
            tvTournamentSummaryType.text = viewmodel.tournament.type
            tvTournamentSummaryScenes.text = viewmodel.tournament.tier
            tvTournamentSummaryPrizepool.text = moneySuffix(viewmodel.tournament.prize.toInt())
            tvTournamentSummaryQuota.text = viewmodel.tournament.totalParticipant.toString()
        }
    }

    private fun setUpJoinTournamentView(){
        binding.apply{
            btnTournamentJoin.setOnClickListener {
                val direction = TournamentDetailFragmentDirections.actionTournamentDetailFragmentToCreateTeamFragment(
                    viewmodel.tournament.game?.id,
                    viewmodel.tournament.id,
                    viewmodel.tournament.maxPlayerInTeam
                )
                findNavController().navigate(direction)
            }
        }
    }

    private fun setupTournamentGameView(){
        binding.apply {
            if(viewmodel.tournament.game?.logo != ""){
                Picasso.with(context)
                    .load(viewmodel.tournament.game?.logo)
                    .into(ivTournamentGameIcon)
            }
            tvTournamentGameName.text= viewmodel.tournament.game?.name
            tvTournamentGameYear.text = viewmodel.tournament.game?.year.toString()
            tvTournamentGameDesc.text = viewmodel.tournament.game?.description
        }
    }

    private fun setupDetailTournamentInfo(){
        binding.apply {
            if(viewmodel.tournament.icon != ""){
                Picasso.with(context)
                    .load(viewmodel.tournament.icon)
                    .into(ivTournamentIcon)
            }
            if(viewmodel.tournament.thumbnail != ""){
                Picasso.with(context)
                    .load(viewmodel.tournament.thumbnail)
                    .into(ivTournamentThumbnail)
            }
            tvTournamentMoreInformationLocation.text = viewmodel.tournament.location
            tvTournamentMoreInformationScene.text = viewmodel.tournament.tier
            tvTournamentMoreInformationQuota.text = viewmodel.tournament.totalParticipant.toString()
            tvTournamentMoreInformationEntryPrice.text = moneySuffix(viewmodel.tournament.fee.toInt())
            tvTournamentMoreInformationMemberRequirement.text = viewmodel.tournament.maxPlayerInTeam.toString()
            tvTournamentMoreInformationType.text = viewmodel.tournament.type
            tvTournamentMoreInformationPrizepool.text = moneySuffix(viewmodel.tournament.prize.toInt())
        }
    }

    private fun sessionManager(user: User){
        Log.d(TAG, "sessionManager: ${viewmodel.tournament.teams}")
        if(viewmodel.tournament.host?.id == user.id){
            binding.btnTournamentJoin.visibility = View.GONE
            binding.llCompleteCompetition.visibility = View.VISIBLE
        }
        if(viewmodel.tournament.teams.isNullOrEmpty()){ } else {
            binding.btnTournamentJoin.visibility = View.GONE
            binding.clMemberIcon.visibility = View.VISIBLE
            setUpMemberIconRecyclerView(GlobalConstant.user)
        }
    }

    private fun setUpMemberIconRecyclerView(user: User){
        binding.rvMemberIcon.apply {
            memberIconAdapter = MemberIconAdapter()
            adapter = memberIconAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
            for(team in viewmodel.tournament.teams!!){
                if(team.users.contains(user)){
                    for(user in team.users){
                        memberIconAdapter.setSelectedMemberList(user)
                    }
                }
            }
        }
    }
}