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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamedup.databinding.FragmentCreateTeamBinding
import com.example.teamedup.home.competition.paymentConfirmation.PaymentConfirmationBottomSheet
import com.example.teamedup.home.competition.successPayDialog.SuccessPayDialog
import com.example.teamedup.repository.model.CreatedTeam
import com.example.teamedup.repository.model.User
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.*
import kotlinx.coroutines.delay
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
    private lateinit var createdTeam : CreatedTeam

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
        submitButtonOnPaymentProofClicked()
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
                viewModel.teamName = etTeamName.text.toString()
                viewModel.teamUsers = viewModel.getAllMemberName(memberNameAdapter.memberList)
                viewModel.accountNo = etTeamBankAccount.text.toString()
                PaymentConfirmationBottomSheet().show(this@CreateTeamFragment.childFragmentManager, "PaymentConfirmationTag")
            }
        }
    }

    private fun getData(){
        lifecycleScope.launch {
            binding.loading.visibility = View.VISIBLE
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
            binding.loading.visibility = View.GONE
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

    private fun submitButtonOnPaymentProofClicked(){
        viewModel.submitButtonClicked.observe(viewLifecycleOwner){
            when(it){
                "notClicked" -> {}
                "clicked" -> {
                    binding.loading.visibility = View.VISIBLE
                    PictureRelatedTools.uploadImage1(viewModel.uploadedPicture)
                    lifecycleScope.launch {
                        if(viewModel.uploadedPicture != null){
                            delay(5000)
                        }
                        viewModel.proofPayment = PictureRelatedTools.uploadedImage1
                        Log.d(TAG, "submitButtonOnPaymentProofClicked: ${viewModel.proofPayment}")
                        createdTeam = CreatedTeam(
                            viewModel.teamName,
                            viewModel.teamUsers,
                            viewModel.proofPayment,
                            viewModel.accountNo
                        )
                        Log.d(TAG, "submitButtonOnPaymentProofClicked: $createdTeam")
                        postData(createTournamentFragmentArgs.gameID!!, createTournamentFragmentArgs.tournamentID!!, createdTeam)
                    }
                }
            }
        }
    }

    private fun postData(gameID : String, tournamentID : String, createdTeam: CreatedTeam){
        lifecycleScope.launch {
            val response = try {
                Log.d(TAG, "postData: $createdTeam")
                RetrofitInstances.api.joinTournament(GlobalConstant.ATHENTICATION_TOKEN,gameID, tournamentID, createdTeam)
            } catch (e : IOException){
                Log.d(TAG, "$e")
                return@launch
            } catch (e : HttpException){
                Log.d(TAG, "Internal Error")
                return@launch
            }
            if(response.isSuccessful && response.body() != null){
                binding.loading.visibility = View.GONE
                SuccessPayDialog().show(requireActivity().supportFragmentManager, "SuccessPayDialogTag")
                Log.d(TAG, "PostData: ${response.body()!!.data}")
                findNavController().popBackStack()
            }else{
                Log.d(TAG, "postData: $response")
                val errorResponse = ErrorUtils.convertApiToGson(response.errorBody()!!.string())
//                val errorMessageFromApi = ArrayList<String>()
                Log.d(TAG, "postData: ${errorResponse.message}")
//                errorMessageFromApi.add(errorResponse.message)
//                errorAdapter.setFilteredGameList(errorMessageFromApi)
            }
        }
    }
}