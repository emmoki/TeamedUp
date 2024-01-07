package com.example.teamedup.home.competition.paymentConfirmation

import android.os.Bundle
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
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.example.teamedup.util.ErrorUtils
import com.example.teamedup.util.GlobalConstant
import com.example.teamedup.util.TAG
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class PaymentConfirmationBottomSheet(private val gameID: String, private val tournamentID : String, private val createdTeam: CreatedTeam) : BottomSheetDialogFragment() {
    lateinit var _binding : BottomSheetDialogPaymentConfirmationBottomSheetBinding
    private val binding get() = _binding
    private val viewModel : CreateTeamViewModel by viewModels()

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
                postData(gameID, tournamentID, createdTeam)
            }
        }
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
                SuccessPayDialog().show(requireActivity().supportFragmentManager, "SuccessPayDialogTag")
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
}