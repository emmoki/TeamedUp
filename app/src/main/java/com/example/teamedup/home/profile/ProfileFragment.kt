package com.example.teamedup.home.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentProfileBinding
import com.example.teamedup.repository.model.Team
import com.example.teamedup.repository.model.User
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.TAG
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ProfileFragment : Fragment() {
    private lateinit var _binding : FragmentProfileBinding
    private val binding get() = _binding
    private lateinit var user : User
    private lateinit var userTournamentHistoryAdapter : UserTournamentHistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHistoryTournament()
        getData()
        setupToolbar()
    }

    private fun setupOtherView(){
        binding.apply {
            llHostedTournament.setOnClickListener { findNavController().navigate(R.id.action_profileFragment_to_hostedTournamentFragment) }
            llJoinedTournament.setOnClickListener { findNavController().navigate(R.id.action_profileFragment_to_participatedTournamentFragment) }
            llClamaiblePrize.setOnClickListener { findNavController().navigate(R.id.action_profileFragment_to_claimablePrizeFragment) }

            btnMainJoinedTournament.setOnClickListener { findNavController().navigate(R.id.action_profileFragment_to_participatedTournamentFragment) }
            btnMainHostedTournament.setOnClickListener { findNavController().navigate(R.id.action_profileFragment_to_hostedTournamentFragment) }

            tvProfileBiography.text = user.biography
            tvProfileContactInfo.text = "(+62) ${user.phoneNum}"
        }
    }

    private fun setupToolbar(){
        binding.apply {
            toolbar.back.setOnClickListener { findNavController().popBackStack() }
            toolbar.tvToolbarTitle.text = "Home"
            toolbar.btnCreate.visibility = View.GONE
        }
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
                user = response.body()!!.data
                val joinedTournament = response.body()!!.data.teamList
                for(team in joinedTournament){
                    Log.d(TAG, "getData: $team")
                    if(team.rank != null){
                        userTournamentHistoryAdapter.teams += team
                        Log.d(TAG, "getData: ${userTournamentHistoryAdapter.teams}")
                    }
                }
                Log.d(TAG, "getData: $user")
                setupOtherView()
            }else{
                Log.d("CompetitionDiscover", "Response no successful")
            }
//            binding.pbCompetitionList.isVisible = false
        }
    }

    private fun setupHistoryTournament(){
        binding.rvTournamentHistoryList.apply {
            userTournamentHistoryAdapter = UserTournamentHistoryAdapter()
            adapter = userTournamentHistoryAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }
}