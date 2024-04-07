package pe.edu.idat.appborabora.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pe.edu.idat.appborabora.data.dto.response.ApiResponse
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.data.network.authenticated.AuthClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val authClient = AuthClient(application.applicationContext)
    private val authService = authClient.getInstance()

    val createProductResponse: MutableLiveData<ApiResponse> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun createProduct(productDTO: ProductDTO) {
        authService.createProduct(productDTO).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    Log.d("ProductViewModel", "Respuesta exitosa al crear producto")
                    createProductResponse.value = response.body()
                } else {
                    Log.d("ProductViewModel", "Error en la respuesta al crear producto: ${response.errorBody()?.string()}")
                    errorMessage.value = "Error al crear el producto: ${response.errorBody()?.string()}"
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("ProductViewModel", "Fallo al crear producto", t)
                errorMessage.value = "Error al crear el producto: ${t.message}"
            }
        })
    }
}


