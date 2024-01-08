package com.example.moodle.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.moodle.MoodleApplication
import com.example.moodle.db.models.ApiResponse
import com.example.moodle.db.models.ApiResponseCredntls
import com.example.moodle.db.models.LoginRequest
import com.example.moodle.db.repositories.MoodleRepository
import com.example.moodle.ui.util.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class MoodleViewsModel(
    app:Application,
    val repository: MoodleRepository
) : AndroidViewModel(app){

    val loginResponse:MutableLiveData<Resource<ApiResponse>> = MutableLiveData()
    val authResponse:MutableLiveData<Resource<ApiResponseCredntls>> = MutableLiveData()
    val logoutResponse:MutableLiveData<Resource<ApiResponseCredntls>> = MutableLiveData()

    fun login(loginRequest: LoginRequest)= GlobalScope.launch {
        try{
            loginResponse.postValue(Resource.Loading())
            if(hasInternetConnection()){
                val response = repository.login(loginRequest)
                Log.d("MoodleAPI",response.body()?.name!!)
                if (response.isSuccessful){
                    response.body()?.let { resultResponse ->
                        loginResponse.postValue(Resource.Success(resultResponse))
                    }
                }else{
                    loginResponse.postValue(Resource.Error(response.message()))
                }
            }else{
                loginResponse.postValue(Resource.Error("No Internet Connection"))
            }
        }catch (e:Throwable){
            when(e){
                is IOException -> loginResponse.postValue(Resource.Error("Network Failure"))
                else -> {
                    Log.e("MoodleAPI",e.message.toString())
                    loginResponse.postValue(Resource.Error("Conversion Errors"))
                }
            }
        }

    }

    fun auth(token:String)= GlobalScope.launch {

    }

    fun logout(token: String)= GlobalScope.launch {

    }

    private fun hasInternetConnection():Boolean {
        val connectivityManager = getApplication<MoodleApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork =connectivityManager.activeNetwork ?: return  false
        val capabilities = connectivityManager.getNetworkCapabilities((activeNetwork))?:return false
        return  when {
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
            else -> false
        }

    }

}