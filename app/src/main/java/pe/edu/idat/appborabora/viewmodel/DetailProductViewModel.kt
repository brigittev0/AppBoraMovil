package pe.edu.idat.appborabora.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.data.dto.response.ProductResponse
import pe.edu.idat.appborabora.data.network.public.PublicClient
import pe.edu.idat.appborabora.data.network.public.PublicService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailProductViewModel : ViewModel() {

    private val _product = MutableLiveData<ProductResponse>()
    val product: LiveData<ProductResponse> get() = _product

    private val service = PublicClient().getInstance()

    fun fetchProduct(id: Int) {
        service.getProductById(id).enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {
                    _product.value = response.body()
                } else {
                    // Manejar el error
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                // Manejar el error
            }
        })
    }
}