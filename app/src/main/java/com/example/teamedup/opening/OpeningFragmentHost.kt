package com.example.teamedup.opening

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentOpeningBinding

class OpeningFragmentHost : Fragment() {
    private lateinit var _binding : FragmentOpeningBinding
    private val binding get() = _binding
    private val fragmentList = ArrayList<Fragment>()
    private lateinit var openingAdapter : OpeningAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOpeningBinding.inflate(layoutInflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openingFragmentListSetup()
        viewPagerSetup()
        otherViewSetup()
    }

    private fun openingFragmentListSetup(){
        fragmentList.add(OpeningFragment1())
        fragmentList.add(OpeningFragment2())
        fragmentList.add(OpeningFragment3())
    }

    private fun viewPagerSetup(){
        openingAdapter = OpeningAdapter(requireActivity().supportFragmentManager, lifecycle)
        binding.vpOpening.adapter = openingAdapter
        openingAdapter.fragmentList = fragmentList
    }

    private fun otherViewSetup(){
        with(binding){
            btnLogIn.setOnClickListener {
                findNavController().navigate(R.id.action_openingFragmentHost_to_loginFragment)
            }
            btnCreateAnAccount.setOnClickListener {
                findNavController().navigate(R.id.action_openingFragmentHost_to_registerFragment)
            }
        }
    }
}