package pe.edu.idat.appborabora.viewmodel

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import pe.edu.idat.appborabora.data.dto.response.ApiResponse
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.data.network.authenticated.AuthClient

class ActualizarProductoViewModel(application: Application) : AndroidViewModel(application) {
    private val authClient = AuthClient(application)
    val product = MutableLiveData<ProductDTO>()
    val updateStatus = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    fun updateProduct(id: Int, productDTO: ProductDTO) {
        authClient.getInstance().updateProduct(id, productDTO).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    updateStatus.value = true
                    message.value = "Producto actualizado con éxito"
                } else {
                    updateStatus.value = false
                    message.value = "Error al actualizar el producto"
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                updateStatus.value = false
                message.value = "Error de red"
            }
        })
    }
}