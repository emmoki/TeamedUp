package com.example.teamedup.home.tournament.paymentConfirmation

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
import com.example.teamedup.databinding.BottomSheetDialogPaymentConfirmationBottomSheetBinding
import com.example.teamedup.home.tournament.createTeam.CreateTeamViewModel
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.TAG
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PaymentConfirmationBottomSheet : BottomSheetDialogFragment() {
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

    private fun setupConfirmTeam(){
        binding.apply {
            btnConfirm.setOnClickListener {
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