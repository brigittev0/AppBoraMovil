package pe.edu.idat.appborabora.viewmodel

import android.app.Application
import android.telecom.Call
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.data.network.public.PublicClient
import retrofit2.Callback
import retrofit2.Response


class ListProdViewModel(application: Application) : AndroidViewModel(application) {
    private val _categoryProducts = MutableLiveData<List<ProductDTO>>()

    private val service = PublicClient().getInstance()

    /*
    fun fetchProductsByCategoryId(categoryId: Int) {
        service.getProductsByCategoryId(categoryId).enqueue(object : Callback<List<ProductDTO>> {
            override fun onResponse(call: Call<List<ProductDTO>>, response: Response<List<ProductDTO>>) {
                if (response.isSuccessful) {
                    _categoryProducts.value = response.body()
                } else {
                    // Manejar el error
                }
            }

            override fun onFailure(call: Call<List<ProductDTO>>, t: Throwable) {
                // Manejar el error
            }
        })
    } */
}