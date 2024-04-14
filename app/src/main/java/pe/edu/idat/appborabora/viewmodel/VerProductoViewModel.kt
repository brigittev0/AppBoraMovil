package pe.edu.idat.appborabora.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.data.network.authenticated.AuthClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerProductoViewModel(application: Application) : AndroidViewModel(application) {
    private val authClient = AuthClient(application)
    val products = MutableLiveData<List<ProductDTO>>()

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        authClient.getInstance().getAllProducts().enqueue(object : Callback<List<ProductDTO>> {
            override fun onResponse(call: Call<List<ProductDTO>>, response: Response<List<ProductDTO>>) {
                if (response.isSuccessful) {
                    products.value = response.body()
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<List<ProductDTO>>, t: Throwable) {
                // Handle failure
            }
        })
    }
}