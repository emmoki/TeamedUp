package com.example.teamedup.home.competition.paymentConfirmation

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.teamedup.databinding.BottomSheetDialogPaymentConfirmationBottomSheetBinding
import com.example.teamedup.home.competition.createTeam.CreateTeamViewModel
import com.example.teamedup.home.competition.successPayDialog.SuccessPayDialog
import com.example.teamedup.repository.model.CreatedTeam
import com.example.teamedup.repository.model.Team
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.ErrorUtils
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.PictureRelatedTools
import com.example.teamedup.util.TAG
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class PaymentConfirmationBottomSheet() : BottomSheetDialogFragment() {
    lateinit var _binding : BottomSheetDialogPaymentConfirmationBottomSheetBinding
    private val binding get() = _binding
    private val createTeamSharedViewModel : CreateTeamViewModel by viewModels(ownerProducer = {requireParentFragment()})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = BottomSheetDialogPaymentConfirmationBottomSheetBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ${createTeamSharedViewModel.teamUsers}")
        setUpAddingProofPayment()
        setupConfirmTeam()
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
//                SuccessCreateDialog().show(parentFragmentManager, "SuccessCreateDialog")
                Log.d(TAG, "PostData: ${response.body()!!.data}")
                findNavController().popBackStack()
                dismiss()
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

    private fun setupConfirmTeam(){
        binding.apply {
            btnConfirm.setOnClickListener {
//                PictureRelatedTools.uploadImage1(createTeamSharedViewModel.uploadedPicture)
//                createTeamSharedViewModel.proofPayment = PictureRelatedTools.uploadedImage1
//                lifecycleScope.launch {
//                    if(createTeamSharedViewModel.uploadedPicture != null){
//                        delay(5000)
//                    }
//                    val createdTeam = CreatedTeam(
//                        createTeamSharedViewModel.teamName,
//                        createTeamSharedViewModel.teamUsers,
//                        createTeamSharedViewModel.proofPayment,
//                        createTeamSharedViewModel.accountNo
//                    )
//                    postData(gameID, tournamentID, createdTeam)
//                }
                createTeamSharedViewModel.setSubmitButtonClicked("clicked")
                dismiss()
            }
        }
    }

    private fun setUpAddingProofPayment(){
        binding.apply {
            createTeamSharedViewModel.picture.observe(viewLifecycleOwner){picture ->
                if(picture != null){
                    ivPaymentProof.setImageBitmap(picture)
                    ivPaymentProof.visibility = View.VISIBLE
                    btnEditPaymentProof.visibility = View.VISIBLE
                    btnPaymentProof.visibility = View.GONE
                }
            }
            btnPaymentProof.setOnClickListener {
                pickImageFromGallery()
            }
        }
    }

    private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        this.startActivityForResult(intent, GlobalConstant.IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GlobalConstant.IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Log.d(TAG, "onActivityResult: ${createTeamSharedViewModel.picture.value}")
            val imageUri = data?.data
            val imageSource = ImageDecoder.createSource(requireActivity().contentResolver, imageUri!!)
            val imageBitmap = ImageDecoder.decodeBitmap(imageSource)
            createTeamSharedViewModel.setPicture(imageBitmap)
            createTeamSharedViewModel.uploadedPicture = imageUri
        }
    }
}