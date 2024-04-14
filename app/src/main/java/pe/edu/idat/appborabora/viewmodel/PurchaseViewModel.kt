package pe.edu.idat.appborabora.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import pe.edu.idat.appborabora.data.dto.response.ApiResponse
import pe.edu.idat.appborabora.data.dto.response.PurchaseDTO
import pe.edu.idat.appborabora.data.network.authenticated.AuthClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PurchaseViewModel(application: Application) : AndroidViewModel(application) {
    private val authClient = AuthClient(application.applicationContext)
    private val authService = authClient.getInstance()

    val purchaseResponse: MutableLiveData<List<PurchaseDTO>> = MutableLiveData()
    val apiResponse: MutableLiveData<ApiResponse> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    //--BUSCAR COMPRAS
    fun fetchAllPurchases(identityDoc: Int) {
        authService.getAllPurchases(identityDoc).enqueue(object : Callback<List<PurchaseDTO>> {
            override fun onResponse(call: Call<List<PurchaseDTO>>, response: Response<List<PurchaseDTO>>) {
                if (response.isSuccessful) {
                    Log.d("PurchaseViewModel", "Respuesta exitosa al obtener las compras del usuario")
                    purchaseResponse.value = response.body()
                } else {
                    Log.d("PurchaseViewModel", "Error en la respuesta al obtener las compras del usuario: ${response.errorBody()?.string()}")
                    errorMessage.value = "Error al obtener las compras del usuario: ${response.errorBody()?.string()}"
                }
            }

            override fun onFailure(call: Call<List<PurchaseDTO>>, t: Throwable) {
                Log.e("PurchaseViewModel", "Fallo al obtener las compras del usuario", t)
                errorMessage.value = "Error al obtener las compras del usuario: ${t.message}"
            }
        })

}
}