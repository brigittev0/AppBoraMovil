package pe.edu.idat.appborabora.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import pe.edu.idat.appborabora.data.dto.response.PurchasetResponse
import pe.edu.idat.appborabora.data.network.authenticated.AuthClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class   DetailPurchaseViewModel(application: Application) : AndroidViewModel(application) {
    private val authClient = AuthClient(application.applicationContext)
    private val authService = authClient.getInstance()


    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val purchaseResponse: MutableLiveData<PurchasetResponse> = MutableLiveData()

    fun fetchPurchaseById(purchaseId: Int) {
        authService.getPurchaseById(purchaseId).enqueue(object : Callback<PurchasetResponse> {
            override fun onResponse(call: Call<PurchasetResponse>, response: Response<PurchasetResponse>) {
                if (response.isSuccessful) {
                    Log.d("PurchaseViewModel", "Respuesta exitosa al obtener la compra")
                    purchaseResponse.value = response.body()
                } else {
                    Log.d("PurchaseViewModel", "Error en la respuesta al obtener la compra: ${response.errorBody()?.string()}")
                    errorMessage.value = "Error al obtener la compra: ${response.errorBody()?.string()}"
                }
            }

            override fun onFailure(call: Call<PurchasetResponse>, t: Throwable) {
                Log.e("PurchaseViewModel", "Fallo al obtener la compra", t)
                errorMessage.value = "Error al obtener la compra: ${t.message}"
            }
        })
    }
}