package pe.edu.idat.appborabora.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.data.dto.response.ProductResponse
import pe.edu.idat.appborabora.data.network.public.PublicClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdCategViewModel : ViewModel() {
    private val _products = MutableLiveData<List<ProductResponse>>()
    val products: LiveData<List<ProductResponse>> get() = _products
    private val service = PublicClient().getInstance()

    fun fetchProductsByCategoryId(categoryId: Int) {
        service.getProductsByCategoryId(categoryId).enqueue(object : Callback<List<ProductResponse>> {
            override fun onResponse(call: Call<List<ProductResponse>>, response: Response<List<ProductResponse>>) {
                if (response.isSuccessful) {
                    _products.value = response.body()
                } else {
                    // Maneja el error
                }
            }

            override fun onFailure(call: Call<List<ProductResponse>>, t: Throwable) {
                // Maneja el error
            }
        })
    }
}