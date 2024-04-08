package pe.edu.idat.appborabora.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.idat.appborabora.data.dto.response.ProductoDashboardResponse
import pe.edu.idat.appborabora.data.network.public.PublicClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductoDashViewModel : ViewModel() {
    private val service = PublicClient().getInstance()
    private val _topSellingProducts = MutableLiveData<List<ProductoDashboardResponse>>()
    val topSellingProducts: LiveData<List<ProductoDashboardResponse>> get() = _topSellingProducts

    init {
        fetchTopSellingProducts()
    }

    private fun fetchTopSellingProducts() {
        service.getTopSellingProducts().enqueue(object : Callback<List<ProductoDashboardResponse>> {
            override fun onResponse(call: Call<List<ProductoDashboardResponse>>, response: Response<List<ProductoDashboardResponse>>) {
                if (response.isSuccessful) {
                    _topSellingProducts.value = response.body()
                } else {
                    // Maneja el error
                }
            }

            override fun onFailure(call: Call<List<ProductoDashboardResponse>>, t: Throwable) {
                // Maneja el error
            }
        })
    }

}