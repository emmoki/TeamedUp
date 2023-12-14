package com.example.teamedup.authentication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentLoginBinding
import com.example.teamedup.home.forum.createForum.SuccessCreateDialog
import com.example.teamedup.repository.model.AccessToken
import com.example.teamedup.repository.model.Forum
import com.example.teamedup.repository.model.format.LoginFormat
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.TAG
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginFragment : Fragment() {
    private lateinit var _binding : FragmentLoginBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOtherView()
    }

    private fun setupOtherView(){
        with(binding){
            toolbar.btnCreate.visibility = View.GONE
            toolbar.tvToolbarTitle.text = "Back"
            tvSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            btnLogin.setOnClickListener {
                postData(etUsername.text.toString(), etPassword.text.toString())
//                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }

    private fun postData(email : String, password: String){
        lifecycleScope.launch {
            val response = try {
                RetrofitInstances.api.login(LoginFormat(email, password))
            } catch (e : IOException){
                Log.d(TAG, "$e")
                return@launch
            } catch (e : HttpException){
                Log.d(TAG, "Internal Error")
                return@launch
            }
            if(response.isSuccessful && response.body() != null){
                val token = response.body()!!
                Log.d(TAG, "postData: $token")
                saveDataInDevice(token.access_token!!)
                GlobalConstant.ATHENTICATION_TOKEN = "Bearer ${token.access_token}"
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }else{
                Log.d(TAG, "Response no successful")
            }
        }
    }

    private fun saveDataInDevice(token : String){
        val sharedPreferences = requireActivity().getSharedPreferences(GlobalConstant.TOKEN_SHAREPREF_TAG, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", token).apply()
    }
}