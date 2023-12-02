package com.example.teamedup.opening

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentOpening1Binding
import com.example.teamedup.databinding.FragmentOpening3Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OpeningFragment3.newInstance] factory method to
 * create an instance of this fragment.
 */
class OpeningFragment3 : Fragment() {
    private lateinit var _binding : FragmentOpening3Binding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOpening3Binding.inflate(layoutInflater,container,false)
        return binding.root
    }
}