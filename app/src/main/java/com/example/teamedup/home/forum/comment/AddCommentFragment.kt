package com.example.teamedup.home.forum.comment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentAddCommentBinding
import com.example.teamedup.home.SharedViewModel
import com.example.teamedup.home.forum.createForum.SuccessCreateDialog
import com.example.teamedup.repository.model.Comment
import com.example.teamedup.repository.model.Forum
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.TAG
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class AddCommentFragment : Fragment() {
    private lateinit var _binding : FragmentAddCommentBinding
    private val binding get() = _binding
    private val sharedViewModel : SharedViewModel by activityViewModels()
    private val addCommentFragmentArgs : AddCommentFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCommentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpOtherView()
    }

    private fun setUpOtherView(){
        binding.apply {
            toolbar.back.setOnClickListener { findNavController().popBackStack() }
        }
        createComment()
    }

    private fun postData(gameID : String, forumID: String, comment: Comment){
        lifecycleScope.launch {
            val response = try {
                RetrofitInstances.api.createComment(GlobalConstant.ATHENTICATION_TOKEN,gameID, forumID, comment)
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
                sharedViewModel.setRestart(true)
//                Log.d(TAG, "getData: ${viewmodel.user}")
            }else{
                Log.d(TAG, "Response no successful")
            }
        }
    }

    private fun createComment(){
        binding.apply {
            toolbar.btnCreate.setOnClickListener {
                postData(
                    addCommentFragmentArgs.gameID,
                    addCommentFragmentArgs.forumID,
                    Comment(
                        "",
                        etCommentContent.text.toString(),
                        0,
                        0,
                        null)
                )
            }
        }
    }
}