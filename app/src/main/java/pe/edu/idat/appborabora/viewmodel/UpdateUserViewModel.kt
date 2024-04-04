package pe.edu.idat.appborabora.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.edu.idat.appborabora.data.dto.request.CreateUserRequest
import pe.edu.idat.appborabora.data.dto.response.ApiResponse
import pe.edu.idat.appborabora.data.network.BoraBoraClient
import retrofit2.Response

class UpdateUserViewModel(private val context: Context) : ViewModel() {

    private val boraBoraClient = BoraBoraClient()

    val apiResponse: MutableLiveData<ApiResponse> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()

    fun updateUser(identityDoc: Int, createUserRequest: CreateUserRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = boraBoraClient.getInstanceWithAuth(context).updateUser(identityDoc, createUserRequest).execute()
                if (response.isSuccessful) {
                    apiResponse.postValue(response.body())
                } else {
                    error.postValue(response.errorBody()?.string())
                }
            } catch (e: Exception) {
                error.postValue(e.message)
            }
        }
    }
}

