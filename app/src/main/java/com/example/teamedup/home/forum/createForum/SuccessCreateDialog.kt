package com.example.teamedup.home.forum.createForum

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.teamedup.databinding.DialogFragmentSuccessPayBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SuccessCreateDialog : DialogFragment() {
    private lateinit var _binding : DialogFragmentSuccessPayBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogFragmentSuccessPayBinding.inflate(inflater)
        setStyle()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.isCancelable = false
        lifecycleScope.launch {
            delay(2000)
            dismiss()
        }
        setupView()
    }

    private fun setupView(){
        binding.tvHeadMessage.text = "Forum Created"
        binding.llFirstMessage.visibility = View.GONE
        binding.tvSecondMessage.visibility = View.GONE
    }

    private fun setStyle(){
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        setStyle(STYLE_NO_FRAME, android.R.style.Theme);
    }
}