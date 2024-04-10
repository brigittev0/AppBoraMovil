package pe.edu.idat.appborabora.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import pe.edu.idat.appborabora.data.dto.response.ProductoEmptyRequest
import pe.edu.idat.appborabora.data.network.authenticated.AuthClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class EliminarProductoViewModel(application: Application) : AndroidViewModel(application) {
    private val authClient = AuthClient(application)
    val product = MutableLiveData<ProductoEmptyRequest>()
    val deleteStatus = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    fun getProductById(id: Int) {
        authClient.getInstance().getProductById(id).enqueue(object : Callback<ProductoEmptyRequest> {
            override fun onResponse(call: Call<ProductoEmptyRequest>, response: Response<ProductoEmptyRequest>) {
                if (response.isSuccessful) {
                    product.value = response.body()
                } else {
                    message.value = "Producto no encontrado"
                }
            }

            override fun onFailure(call: Call<ProductoEmptyRequest>, t: Throwable) {
                message.value = "Error al obtener el producto"
            }
        })
    }

    fun deleteProduct(id: Int) {
        authClient.getInstance().deleteProduct(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                deleteStatus.value = response.isSuccessful
                message.value = "Producto eliminado con éxito"
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                message.value = "Error al eliminar el producto"
            }
        })
    }
}