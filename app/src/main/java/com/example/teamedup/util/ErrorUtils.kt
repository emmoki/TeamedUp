package com.example.teamedup.util

import android.util.Log
import com.example.teamedup.authentication.LoginFragment
import com.example.teamedup.repository.model.ApiError
import com.example.teamedup.repository.remoteData.retrofitSetup.RetrofitInstances
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response


object ErrorUtils {
    fun convertApiToGson(errorBodyString : String) : ApiError{
        val gson = Gson()
        val type = object : TypeToken<ApiError>() {}.type
        Log.d(TAG, "errorBodyString: $errorBodyString")
        var errorResponse: ApiError = gson.fromJson(errorBodyString, type)
        Log.d(TAG, "errorResponse: $errorBodyString")

        return errorResponse
    }
}