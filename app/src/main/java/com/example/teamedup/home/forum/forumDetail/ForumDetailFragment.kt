package com.example.teamedup.home.forum.forumDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamedup.databinding.FragmentForumDetailBinding
import com.example.teamedup.home.forum.comment.CommentItemAdapter
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.TAG
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ForumDetailFragment : Fragment() {
    private lateinit var _binding : FragmentForumDetailBinding
    private val binding get() = _binding
    private val forumDetailFragmentArgs : ForumDetailFragmentArgs by navArgs()
    private val viewModel : ForumDetailViewmodel by viewModels()
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
        createCommentBtn()
    }

    private fun getForumData(gameID : String, forumID : String){
        lifecycleScope.launch {
//            binding.pbCompetitionDetail.isVisible = true
            val response = try {
                Log.d(TAG, "onViewCreated: Game : ${forumDetailFragmentArgs.gameID}")
                Log.d(TAG, "onViewCreated: Forum : ${forumDetailFragmentArgs.forumID}")
                RetrofitInstances.api.getForumDetail(GlobalConstant.ATHENTICATION_TOKEN,gameID, forumID)
            } catch (e : IOException){
                Log.d(TAG, "$e")
                return@launch
            } catch (e : HttpException){
                Log.d(TAG, "Internal Error")
                return@launch
            }
            if(response.isSuccessful && response.body() != null){
                viewModel.forum = response.body()!!.data
                Log.d(TAG, "getData: ${viewModel.forum}")
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
                RetrofitInstances.api.getForumComment(GlobalConstant.ATHENTICATION_TOKEN,gameID, forumID)
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
            Picasso.with(context)
                .load(viewModel.forum.user?.picture)
                .into(ivAuthor)
            tvAuthorName.text = viewModel.forum.user?.name
            Picasso.with(context)
                .load(viewModel.forum.thumbnail)
                .into(ivForumImage)
            tvForumUpVote.text = viewModel.forum.upVote.toString()
            tvForumDownVote.text = viewModel.forum.downVote.toString()
            tvForumContent.text = viewModel.forum.content
        }
    }

    private fun createCommentBtn(){
        binding.apply {
            clAdd.setOnClickListener {
                val direction = ForumDetailFragmentDirections
                    .actionFragmentDetailFragmentToAddCommentFragment(
                        forumDetailFragmentArgs.gameID,
                        forumDetailFragmentArgs.forumID
                    )
                findNavController().navigate(direction)
            }
        }
    }
}