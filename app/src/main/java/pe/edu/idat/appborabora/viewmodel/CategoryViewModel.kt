package pe.edu.idat.appborabora.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.idat.appborabora.data.dto.response.CategoryResponse
import pe.edu.idat.appborabora.data.network.public.PublicClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel : ViewModel() {
    private val _categories = MutableLiveData<List<CategoryResponse>>()
    val categories: LiveData<List<CategoryResponse>> get() = _categories

    private val service = PublicClient().getInstance()

    fun fetchAllCategories() {
        service.getAllCategories().enqueue(object : Callback<List<CategoryResponse>> {
            override fun onResponse(call: Call<List<CategoryResponse>>, response: Response<List<CategoryResponse>>) {
                if (response.isSuccessful) {
                    _categories.value = response.body()
                } else {
                    // Maneja el error
                }
            }

            override fun onFailure(call: Call<List<CategoryResponse>>, t: Throwable) {
                // Maneja el error
            }
        })
    }
}