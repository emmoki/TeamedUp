package com.example.teamedup.home.forum.createForum

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentCreateForumBinding
import com.example.teamedup.home.SharedViewModel
import com.example.teamedup.repository.model.Forum
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.TAG
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class CreateForumFragment : Fragment() {
    private lateinit var _binding : FragmentCreateForumBinding
    private val binding get() = _binding
    private val sharedViewModel : SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateForumBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpOtherView()
    }

    private fun setUpOtherView(){
        binding.apply {
            toolbar.tvToolbarTitle.text = "Create Forum"
            toolbar.btnCreate.text = "Post"
            toolbar.back.setOnClickListener {
                findNavController().popBackStack()
            }
            toolbar.btnCreate.setOnClickListener {
                sharedViewModel.game.observe(viewLifecycleOwner){game ->
                    postData(game, Forum(null,etForumTitle.text.toString(),etForumContent.text.toString(),0,0,null,null))
                }
            }
        }
    }

    private fun postData(gameID : String,forum: Forum){
        lifecycleScope.launch {
            val response = try {
                RetrofitInstances.api.createForums(gameID, forum)
            } catch (e : IOException){
                Log.d(TAG, "$e")
                return@launch
            } catch (e : HttpException){
                Log.d(TAG, "Internal Error")
                return@launch
            }
            if(response.isSuccessful && response.body() != null){
                SuccessCreateDialog().show(parentFragmentManager, "SuccessCreateDialog")
                findNavController().popBackStack()
//                Log.d(TAG, "getData: ${viewmodel.user}")
            }else{
                Log.d(TAG, "Response no successful")
            }
        }
    }
}