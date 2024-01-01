package com.example.teamedup.home.competition.createTeam

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentCreateTeamBinding
import com.example.teamedup.home.GameAdapter
import com.example.teamedup.home.competition.paymentConfirmation.PaymentConfirmationBottomSheet
import com.example.teamedup.repository.model.User
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.TAG
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CreateTeamFragment : Fragment() {
    private lateinit var _binding : FragmentCreateTeamBinding
    private val binding get() = _binding
    private lateinit var memberIconAdapter: MemberIconAdapter
    private lateinit var memberNameAdapter: MemberNameAdapter
    private val memberList = ArrayList<User>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateTeamBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMemberIconRecyclerView()
        setUpMemberNameRecyclerView()
        setUpOtherView()
        setupSearchUser()
    }

    private fun setUpMemberIconRecyclerView(){
        binding.rvMemberIcon.apply {
            memberIconAdapter = MemberIconAdapter()
            adapter = memberIconAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
//            gameAdapter.gameListener = this@HomeFragment
        }
    }

    private fun setUpMemberNameRecyclerView(){
        binding.rvMemberName.apply {
            memberNameAdapter = MemberNameAdapter()
            adapter = memberNameAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)
//            gameAdapter.gameListener = this@HomeFragment
        }
    }

    private fun setUpOtherView(){
        with(binding){
            tvCreateGameMemberRequirement.text = "${memberList.size} / 10"
            toolbarCreateTeam.btnCreate.setOnClickListener {
                PaymentConfirmationBottomSheet(memberList).show(requireActivity().supportFragmentManager, "PaymentConfirmationTag")
            }
        }
    }

    private fun setupSearchUser(){
        binding.apply {
            btnSearchUser.setOnClickListener {
                Log.d(TAG, "setupSearchUser: ${etSearch.text}")
                getData(etSearch.text.toString())
            }
        }
    }

    private fun getData(searchedUser : String){
        lifecycleScope.launch {
//            binding.pbGameList.isVisible = true
            val response = try {
                RetrofitInstances.api.getUserList(GlobalConstant.ATHENTICATION_TOKEN, searchedUser)
            } catch (e : IOException){
                Log.d("HomeFragment", "$e")
                return@launch
            } catch (e : HttpException){
                Log.d("HomeFragment", "Internal Error")
                return@launch
            }

            if(response.isSuccessful && response.body() != null){
//                gameAdapter.games = response.body()!!.data
//                gameSearchAdapter.games = response.body()!!.data
//                searchedGames = response.body()!!.data
                Log.d(TAG, "getData: ${response.body()}")
            }else{
                Log.d("HomeFragment", "Response no successful")
            }
//            binding.pbGameList.isVisible = false
        }
    }

}