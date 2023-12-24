package com.example.teamedup.authentication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamedup.R
import com.example.teamedup.databinding.FragmentLoginBinding
import com.example.teamedup.home.forum.ForumAdapter
import com.example.teamedup.repository.model.ApiError
import com.example.teamedup.repository.model.format.LoginFormat
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.ErrorAdapter
import com.example.teamedup.util.ErrorUtils
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.TAG
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class LoginFragment : Fragment() {
    private lateinit var _binding : FragmentLoginBinding
    private val binding get() = _binding
    private val viewModel : LoginViewModel by viewModels()
    private lateinit var errorAdapter : ErrorAdapter

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
        setupAlertView()
    }

    private fun setupOtherView(){
        with(binding){
            toolbar.btnCreate.visibility = View.GONE
            toolbar.tvToolbarTitle.text = "Back"
            tvSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            btnLogin.setOnClickListener {
                Log.d(TAG, "setupOtherView: ${viewModel.loginValidation(LoginFormat(etUsername.text.toString(), etPassword.text.toString()))}")
                val errorMassageList = viewModel.loginValidation(LoginFormat(etUsername.text.toString(), etPassword.text.toString()))
                when (errorMassageList.isEmpty()){
                    true -> { postData(etUsername.text.toString(), etPassword.text.toString()) }
                    false -> { errorAdapter.setFilteredGameList(errorMassageList) }
                }
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
                val errorResponse = ErrorUtils.convertApiToGson(response)
                val errorMessageFromApi = ArrayList<String>()
                Log.d(TAG, "postData: ${errorResponse.message}")
                errorMessageFromApi.add(errorResponse.message)
                errorAdapter.setFilteredGameList(errorMessageFromApi)
            }
        }
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

    private fun saveDataInDevice(token : String){
        val sharedPreferences = requireActivity().getSharedPreferences(GlobalConstant.TOKEN_SHAREPREF_TAG, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", token).apply()
    }
}