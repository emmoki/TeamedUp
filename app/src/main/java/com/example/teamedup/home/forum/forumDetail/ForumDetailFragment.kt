package com.example.teamedup.home.forum.forumDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamedup.databinding.FragmentForumDetailBinding
import com.example.teamedup.home.forum.comment.CommentItemAdapter
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.TAG
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ForumDetailFragment : Fragment() {
    private lateinit var _binding : FragmentForumDetailBinding
    private val binding get() = _binding
    private val forumDetailFragmentArgs : ForumDetailFragmentArgs by navArgs()
    private val viewmodel : ForumDetailViewmodel by viewModels()
    private lateinit var commentAdapter : CommentItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForumDetailBinding.inflate(layoutInflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getForumData(forumDetailFragmentArgs.gameID, forumDetailFragmentArgs.forumID)
        getCommentData(forumDetailFragmentArgs.gameID, forumDetailFragmentArgs.forumID)
        setUpCommentRecyclerView()
    }

    private fun getForumData(gameID : String, forumID : String){
        lifecycleScope.launch {
//            binding.pbCompetitionDetail.isVisible = true
            val response = try {
                Log.d(TAG, "onViewCreated: Game : ${forumDetailFragmentArgs.gameID}")
                Log.d(TAG, "onViewCreated: Forum : ${forumDetailFragmentArgs.forumID}")
                RetrofitInstances.api.getForumDetail(gameID, forumID)
            } catch (e : IOException){
                Log.d(TAG, "$e")
                return@launch
            } catch (e : HttpException){
                Log.d(TAG, "Internal Error")
                return@launch
            }
            if(response.isSuccessful && response.body() != null){
                viewmodel.forum = response.body()!!.data
                Log.d(TAG, "getData: ${viewmodel.forum}")
                setUpView()
            }else{
                Log.d(TAG, "Response no successful")
            }
//            binding.pbCompetitionDetail.isVisible = false
        }
    }

    private fun getCommentData(gameID : String, forumID: String){
        lifecycleScope.launch {
//            binding.pbCompetitionDetail.isVisible = true
            val response = try {
                Log.d(TAG, "onViewCreated: Game : ${forumDetailFragmentArgs.gameID}")
                Log.d(TAG, "onViewCreated: Forum : ${forumDetailFragmentArgs.forumID}")
                RetrofitInstances.api.getForumComment(gameID, forumID)
            } catch (e : IOException){
                Log.d(TAG, "$e")
                return@launch
            } catch (e : HttpException){
                Log.d(TAG, "Internal Error")
                return@launch
            }
            if(response.isSuccessful && response.body() != null){
                commentAdapter.comments = response.body()!!.data
            }else{
                Log.d(TAG, "Response no successful")
            }
//            binding.pbCompetitionDetail.isVisible = false
        }
    }

    private fun setUpCommentRecyclerView(){
        binding.rvCommentList.apply {
            commentAdapter = CommentItemAdapter()
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false)
        }
    }

    private fun setUpView(){
        binding.apply {
            tvAuthorName.text = viewmodel.forum.user?.name
            tvForumUpVote.text = viewmodel.forum.upVote.toString()
            tvForumDownVote.text = viewmodel.forum.downVote.toString()
            tvForumContent.text = viewmodel.forum.content
        }
    }
}