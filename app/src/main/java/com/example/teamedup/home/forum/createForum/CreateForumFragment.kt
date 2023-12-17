package com.example.teamedup.home.forum.createForum

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.teamedup.databinding.FragmentCreateForumBinding
import com.example.teamedup.home.SharedViewModel
import com.example.teamedup.repository.model.Forum
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.PictureRelatedTools
import com.example.teamedup.util.TAG
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class CreateForumFragment : Fragment() {
    private lateinit var _binding : FragmentCreateForumBinding
    private val binding get() = _binding
    private val sharedViewModel : SharedViewModel by activityViewModels()
    private val viewModel : CreateForumViewModel by viewModels()
    private lateinit var createForum : Forum

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
            viewModel.picture.observe(viewLifecycleOwner){picture ->
                if(picture != null){
                    Log.d(TAG, "setUpOtherView: ${PictureRelatedTools.convertBitmapToBase64(picture)}")
                    ivForumThumbnail.setImageBitmap(picture)
                    viewModel.base64Image = PictureRelatedTools.convertBitmapToBase64(picture)
                    ivForumThumbnail.visibility = View.VISIBLE
                    btnEditForumThumbnail.visibility = View.VISIBLE
                    btnAddForumThumbnail.visibility = View.GONE
                }
            }
            toolbar.tvToolbarTitle.text = "Create Forum"
            toolbar.btnCreate.text = "Post"
            toolbar.back.setOnClickListener {
                findNavController().popBackStack()
            }
            toolbar.btnCreate.setOnClickListener {
                createForum = Forum(
                    null,
                    etForumTitle.text.toString(),
                    etForumContent.text.toString(),
                    0,
                    0,
                    null,
                    viewModel.base64Image,
                    null
                )
                Log.d("CreatedTournament", "Created Forum: $createForum")
                sharedViewModel.game.observe(viewLifecycleOwner){game ->
                    postData(game, createForum)
                }
            }
            btnAddForumThumbnail.setOnClickListener {
                pickImageFromGallery()
            }
            btnEditForumThumbnail.setOnClickListener {
                pickImageFromGallery()
            }
        }
    }

    private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        this.startActivityForResult(intent, GlobalConstant.IMAGE_REQUEST_CODE)
    }

    private fun postData(gameID : String,forum: Forum){
        lifecycleScope.launch {
            val response = try {
                RetrofitInstances.api.createForums(GlobalConstant.ATHENTICATION_TOKEN,gameID, forum)
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
                Log.d(TAG, "postData: ${response.message()}")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GlobalConstant.IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            Log.d(TAG, "onActivityResult: ${viewModel.picture.value}")
            val imageUri = data?.data
            val imageSource = ImageDecoder.createSource(requireActivity().contentResolver, imageUri!!)
            val imageBitmap = ImageDecoder.decodeBitmap(imageSource)
            viewModel.setPicture(imageBitmap)
        }
    }
}