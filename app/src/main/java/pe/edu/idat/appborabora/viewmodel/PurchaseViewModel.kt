package pe.edu.idat.appborabora.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import pe.edu.idat.appborabora.data.dto.response.ApiResponse
import pe.edu.idat.appborabora.data.dto.response.PurchasetResponse
import com.google.gson.Gson
import pe.edu.idat.appborabora.data.dto.request.PurchaseRequest
import pe.edu.idat.appborabora.data.network.authenticated.AuthClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PurchaseViewModel(application: Application) : AndroidViewModel(application) {
    private val authClient = AuthClient(application.applicationContext)
    private val authService = authClient.getInstance()


    val apiResponse: MutableLiveData<ApiResponse> = MutableLiveData()

    val createPurchaseResponse: MutableLiveData<ApiResponse> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    //--BUSCAR COMPRAS
    val purchaseResponse: MutableLiveData<List<PurchasetResponse>> = MutableLiveData()

    fun fetchAllPurchases(identityDoc: Int) {
        authService.getAllPurchases(identityDoc).enqueue(object : Callback<List<PurchasetResponse>> {
            override fun onResponse(call: Call<List<PurchasetResponse>>, response: Response<List<PurchasetResponse>>) {
                if (response.isSuccessful) {
                    Log.d("PurchaseViewModel", "Respuesta exitosa al obtener las compras del usuario")
                    purchaseResponse.value = response.body()
                } else {
                    Log.d("PurchaseViewModel", "Error en la respuesta al obtener las compras del usuario: ${response.errorBody()?.string()}")
                    errorMessage.value = "Error al obtener las compras del usuario: ${response.errorBody()?.string()}"
                }
            }

            override fun onFailure(call: Call<List<PurchasetResponse>>, t: Throwable) {
                Log.e("PurchaseViewModel", "Fallo al obtener las compras del usuario", t)
                errorMessage.value = "Error al obtener las compras del usuario: ${t.message}"
            }
        })
    }

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



