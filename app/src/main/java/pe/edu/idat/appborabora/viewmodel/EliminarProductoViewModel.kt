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

    fun getProductById(id: Int) {
        authClient.getInstance().getProductById(id).enqueue(object : Callback<ProductoEmptyRequest> {
            override fun onResponse(call: Call<ProductoEmptyRequest>, response: Response<ProductoEmptyRequest>) {
                if (response.isSuccessful) {
                    product.value = response.body()
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<ProductoEmptyRequest>, t: Throwable) {
                // Handle failure
            }
        })
    }

    fun deleteProduct(id: Int) {
        authClient.getInstance().deleteProduct(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                deleteStatus.value = response.isSuccessful
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Handle failure
            }
        })
    }
}