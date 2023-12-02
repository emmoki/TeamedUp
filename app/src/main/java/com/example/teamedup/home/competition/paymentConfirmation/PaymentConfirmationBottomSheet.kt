package com.example.teamedup.home.competition.paymentConfirmation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teamedup.databinding.BottomSheetDialogPaymentConfirmationBottomSheetBinding
import com.example.teamedup.home.competition.successPayDialog.SuccessPayDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PaymentConfirmationBottomSheet : BottomSheetDialogFragment() {
    lateinit var _binding : BottomSheetDialogPaymentConfirmationBottomSheetBinding
    private val binding get() = _binding

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
        with(binding){
            btnConfirm.setOnClickListener {
                SuccessPayDialog().show(requireActivity().supportFragmentManager, "SuccessPayDialogTag")
                dismiss()
            }
        }
    }
}