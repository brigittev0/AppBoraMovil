package pe.edu.idat.appborabora.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import pe.edu.idat.appborabora.data.dto.request.PurchaseRequest
import pe.edu.idat.appborabora.data.dto.response.ApiResponse
import pe.edu.idat.appborabora.data.network.authenticated.AuthClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PurchaseViewModel(application: Application) : AndroidViewModel(application) {
    private val authClient = AuthClient(application.applicationContext)
    private val authService = authClient.getInstance()

    val createPurchaseResponse: MutableLiveData<ApiResponse> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun createPurchase(orderType: String, purchaseRequest: PurchaseRequest) {
        val call: Call<ApiResponse>
        if (orderType.equals("PICKUP", ignoreCase = true)) {
            call = authService.createPickUpOrder(purchaseRequest)
        } else if (orderType.equals("DELIVERY", ignoreCase = true)) {
            call = authService.createDeliveryOrder(purchaseRequest)
        } else {
            throw IllegalArgumentException("Invalid order type: $orderType")
        }

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    Log.d("PurchaseViewModel", "Respuesta exitosa al crear compra")
                    createPurchaseResponse.value = response.body()
                } else {
                    val gson = Gson()
                    val errorResponse = response.errorBody()?.let {
                        gson.fromJson(it.string(), ApiResponse::class.java)
                    }
                    Log.d("PurchaseViewModel", "Error en la respuesta al crear compra: ${response.errorBody()?.string()}")
                    errorMessage.value = "${errorResponse?.message}"
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("PurchaseViewModel", "Fallo al crear compra", t)
                errorMessage.value = "${t.message}"
            }
        })
    }
}