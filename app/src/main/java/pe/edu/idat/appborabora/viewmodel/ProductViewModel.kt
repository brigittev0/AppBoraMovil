package pe.edu.idat.appborabora.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import pe.edu.idat.appborabora.data.dto.response.ApiResponse
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.data.network.authenticated.AuthClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val authClient = AuthClient(application)
    private val authService = authClient.getInstance()

    val createProductResponse: MutableLiveData<ApiResponse> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    //--CREAR PRODUCTO
    fun createProduct(productDTO: ProductDTO) {
        authService.createProduct(productDTO).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    Log.d("ProductViewModel", "Respuesta exitosa al crear producto")
                    createProductResponse.value = response.body()
                } else {
                    val gson = Gson()
                    val errorResponse = response.errorBody()?.let {
                        gson.fromJson(it.string(), ApiResponse::class.java)
                    }
                    Log.d("ProductViewModel", "Error en la respuesta al crear producto: ${response.errorBody()?.string()}")
                    errorMessage.value = "${errorResponse?.message}"
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("ProductViewModel", "Fallo al crear producto", t)
                errorMessage.value = "${t.message}"
            }
        })
    }
}


