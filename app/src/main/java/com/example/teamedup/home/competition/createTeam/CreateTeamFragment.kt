package com.example.teamedup.home.competition.createTeam

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamedup.databinding.FragmentCreateTeamBinding
import com.example.teamedup.home.competition.paymentConfirmation.PaymentConfirmationBottomSheet
import com.example.teamedup.repository.model.Team
import com.example.teamedup.repository.model.User
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.TAG
import com.example.teamedup.util.UserDeleteRecyclerViewClickListener
import com.example.teamedup.util.UserRecyclerViewClickListener
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class CreateTeamFragment : Fragment(), UserRecyclerViewClickListener,
    UserDeleteRecyclerViewClickListener {
    private lateinit var _binding : FragmentCreateTeamBinding
    private val binding get() = _binding
    private lateinit var memberIconAdapter: MemberIconAdapter
    private lateinit var memberNameAdapter: MemberNameAdapter
    private lateinit var searchedUserAdapter : SearchUserAdapter
    private lateinit var searchedUser : List<User>
    private val viewModel : CreateTeamViewModel by viewModels()
    private val createTournamentFragmentArgs : CreateTeamFragmentArgs by navArgs()

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
        getData()
        setUpMemberIconRecyclerView()
        setUpMemberNameRecyclerView()
        setUpOtherView()
        setupUserSearchRecyclerView()
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
            memberNameAdapter.deleteUserListener = this@CreateTeamFragment
        }
    }

    private fun setUpOtherView(){
        with(binding){
            viewModel.memberHandlerCounter.observe(viewLifecycleOwner){
                tvCreateGameMemberRequirement.text = "$it / ${createTournamentFragmentArgs.memberMax}"
            }
            toolbarCreateTeam.btnCreate.setOnClickListener {
                PaymentConfirmationBottomSheet(createTournamentFragmentArgs.gameID!!, createTournamentFragmentArgs.tournamentID!!, Team(etTeamName.text.toString(), viewModel.getAllMemberName(memberNameAdapter.memberList))).show(requireActivity().supportFragmentManager, "PaymentConfirmationTag")
            }
        }
    }

    private fun getData(){
        lifecycleScope.launch {
//            binding.pbGameList.isVisible = true
            val response = try {
                RetrofitInstances.api.getUserList(GlobalConstant.ATHENTICATION_TOKEN)
            } catch (e : IOException){
                Log.d("HomeFragment", "$e")
                return@launch
            } catch (e : HttpException){
                Log.d("HomeFragment", "Internal Error")
                return@launch
            }

            if(response.isSuccessful && response.body() != null){
//                gameAdapter.games = response.body()!!.data
                searchedUserAdapter.users = response.body()!!.data
                searchedUser = response.body()!!.data
                Log.d(TAG, "getData: ${response.body()}")
            }else{
                Log.d("HomeFragment", "Response no successful")
            }
//            binding.pbGameList.isVisible = false
        }
    }

    private fun setupUserSearchRecyclerView(){
        binding.apply {
            rvSearchedUser.apply {
                setHasFixedSize(true)
                searchedUserAdapter = SearchUserAdapter()
                adapter = searchedUserAdapter
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false)
                searchedUserAdapter.searchedUserListener = this@CreateTeamFragment
            }
            binding.etSearch.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(searchedGame: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    filterList(searchedGame.toString())
                }

                override fun afterTextChanged(p0: Editable?) {}

            })
        }
    }

    private fun filterList(query: String) {
        binding.rvSearchedUser.visibility = View.VISIBLE
        val filteredList = ArrayList<User>()
        for (searchedUserLetter in searchedUser) {
            if (searchedUserLetter.name.lowercase(Locale.ROOT).contains(query.lowercase())) {
                filteredList.add(searchedUserLetter)
            }
        }
        searchedUserAdapter.setFilteredGameList(filteredList)
    }

    override fun onItemClicked(view: View, user: User) {
        Log.d(TAG, "onItemClicked: ${user.id}")
        if(viewModel.memberCounter == createTournamentFragmentArgs.memberMax){
            Log.d(TAG, "onItemClicked: FULL")
        }else {
            viewModel.memberCounter++
            viewModel.setCounterHandler(viewModel.memberCounter)
            memberIconAdapter.setSelectedMemberList(user)
            memberNameAdapter.setSelectedMemberList(user)
        }
    }

    override fun onUserDeleteClicked(view: View, user: User) {
        viewModel.memberCounter--
        viewModel.setCounterHandler(viewModel.memberCounter)
        memberIconAdapter.setDeleteMemberList(user)
        memberNameAdapter.setDeleteMemberList(user)
    }
}