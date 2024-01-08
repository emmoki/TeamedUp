package com.example.teamedup.authentication

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentRegisterBinding
import com.example.teamedup.repository.model.format.RegisterFormat
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.*
import com.example.teamedup.util.PictureRelatedTools.convertBitmapToBase64
import com.example.teamedup.util.PictureRelatedTools.uploadImage1
import com.example.teamedup.util.PictureRelatedTools.uploadedImage1
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RegisterFragment : Fragment() {
    private lateinit var _binding : FragmentRegisterBinding
    private lateinit var errorAdapter : ErrorAdapter
    private val binding get() = _binding
    private val viewModel : RegisterViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    private fun postData(registerUser : RegisterFormat){
        lifecycleScope.launch {
            val response = try {
                RetrofitInstances.api.register(registerUser)
            } catch (e : IOException){
                Log.d(TAG, "$e")
                return@launch
            } catch (e : HttpException){
                Log.d(TAG, "Internal Error")
                return@launch
            }
            if(response.isSuccessful && response.body() != null){
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }else{
                Log.d(TAG, "postData: $response")
                val errorResponse = ErrorUtils.convertApiToGson(response.errorBody()!!.string())
                val errorMessageFromApi = ArrayList<String>()
                Log.d(TAG, "postData: ${errorResponse.message}")
                errorMessageFromApi.add(errorResponse.message)
                errorAdapter.setFilteredGameList(errorMessageFromApi)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRegisteringUser()
        setupUserIcon()
        setupAlertView()
    }

    private fun setupToolbar(){
        with(binding){
            toolbar.btnCreate.visibility = View.GONE
            toolbar.tvToolbarTitle.text = "Login"
            toolbar.back.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    private fun setupRegisteringUser(){
        binding.apply {
            btnConfirm.setOnClickListener {
                ViewUtils.hideKeyboard(requireActivity())
                binding.loading.visibility = View.VISIBLE
                uploadImage1(viewModel.uploadedImage)
                lifecycleScope.launch {
                    if(viewModel.uploadedImage != null){
                        delay(5000)
                    }
                    val registerUser = RegisterFormat(
                        etName.text.toString(),
                        etEmail.text.toString(),
                        etPassword.text.toString(),
                        uploadedImage1,
                        etUserBiography.text.toString(),
                        etPhoneNumber.text.toString()
                    )
                    Log.d(TAG, "setupRegisteringUser: $registerUser")
                    val errorMassageList = viewModel.registerValidation(registerUser)
                    when(errorMassageList.isEmpty()){
                        true -> {
                            errorMessageList.rvErrorList.visibility = View.GONE
                            postData(registerUser)
                            loading.visibility = View.GONE
                        }
                        false -> {
                            errorMessageList.rvErrorList.visibility = View.VISIBLE
                            errorAdapter.setFilteredGameList(errorMassageList)
                            loading.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }


    private fun setupUserIcon(){
        binding.apply {
            btnAddUserProfileIcon.setOnClickListener {
                pickImageFromGallery()
            }
            btnEditUserProfileIcon.setOnClickListener {
                pickImageFromGallery()
            }
            viewModel.picture.observe(viewLifecycleOwner){picture ->
                if(picture != null){
                    Log.d(TAG, "setUpOtherView: ${convertBitmapToBase64(picture)}")
                    ivUserProfileIcon.setImageBitmap(picture)
                    ivUserProfileIcon.visibility = View.VISIBLE
                    btnEditUserProfileIcon.visibility = View.VISIBLE
                    btnAddUserProfileIcon.visibility = View.GONE
                }
            }
        }
    }

    private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        this.startActivityForResult(intent, GlobalConstant.IMAGE_REQUEST_CODE)
    }

    private fun setupAlertView(){
        binding.errorMessageList.rvErrorList.apply {
            errorAdapter = ErrorAdapter()
            adapter = errorAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GlobalConstant.IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Log.d(TAG, "onActivityResult: ${viewModel.picture.value}")
            val imageUri = data?.data
            val imageSource = ImageDecoder.createSource(requireActivity().contentResolver, imageUri!!)
            val imageBitmap = ImageDecoder.decodeBitmap(imageSource)
            viewModel.setPicture(imageBitmap)
            viewModel.uploadedImage = imageUri
        }
    }
}