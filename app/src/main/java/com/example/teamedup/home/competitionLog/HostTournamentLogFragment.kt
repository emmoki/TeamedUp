package com.example.teamedup.home.competitionLog

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentHostTournamentLogBinding
import com.example.teamedup.databinding.FragmentHostedTournamentBinding
import com.example.teamedup.repository.model.format.UpdateRank
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.ErrorUtils
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.TAG
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.*

class HostTournamentLogFragment : Fragment() {
    private lateinit var _binding : FragmentHostTournamentLogBinding
    private val binding get() = _binding
    private lateinit var listTeamAdapter : TeamRankAdapter
    private val hostTournamentLogFragmentArgs : HostTournamentLogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHostTournamentLogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpParticipatedTeamList()
        setupData()
        postData()
    }

    private fun setUpParticipatedTeamList(){
        binding.rvRankingTeam.apply {
            listTeamAdapter = TeamRankAdapter()
            adapter = listTeamAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun setupData(){
        val nameList = hostTournamentLogFragmentArgs.participatedTeamList.toList()
        listTeamAdapter.teamList = nameList
    }

    private fun postData(){
        binding.apply {
            btnLogin.setOnClickListener {
                Log.d(TAG, "postData: ${listTeamAdapter.rankingResult}")
                lifecycleScope.launch {
                    val response = try {
                        RetrofitInstances.api.updateRank(GlobalConstant.ATHENTICATION_TOKEN, hostTournamentLogFragmentArgs.gameID, hostTournamentLogFragmentArgs.tournamentID , UpdateRank(listTeamAdapter.rankingResult))
                    } catch (e : IOException){
                        Log.d(TAG, "$e")
                        return@launch
                    } catch (e : HttpException){
                        Log.d(TAG, "Internal Error")
                        return@launch
                    }
                    if(response.isSuccessful && response.body() != null){
                        findNavController().navigate(R.id.action_hostTournamentLogFragment_to_homeFragment)
                    }else{
                        Log.d(TAG, "postData: $response")
//                        val errorResponse = ErrorUtils.convertApiToGson(response.errorBody()!!.string())
//                        val errorMessageFromApi = ArrayList<String>()
//                        Log.d(TAG, "postData: ${errorResponse.message}")
//                        errorMessageFromApi.add(errorResponse.message)
//                        errorAdapter.setFilteredGameList(errorMessageFromApi)
                    }
                }
            }
        }
    }
}