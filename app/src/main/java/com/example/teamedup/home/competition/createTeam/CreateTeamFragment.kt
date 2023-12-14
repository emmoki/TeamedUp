package com.example.teamedup.home.competition.createTeam

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentCreateTeamBinding
import com.example.teamedup.home.GameAdapter
import com.example.teamedup.home.competition.paymentConfirmation.PaymentConfirmationBottomSheet
import com.example.teamedup.repository.model.User

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
        getData()
        setUpOtherView()
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
                PaymentConfirmationBottomSheet().show(requireActivity().supportFragmentManager, "PaymentConfirmationTag")
            }
        }
    }

    private fun getData(){
        memberList.add(User("TONO", "SDfsdfdsf", "Sdfsdfdsfds", null, null, null, null ))
        memberIconAdapter.memberList = memberList
        memberNameAdapter.memberList = memberList
    }

}