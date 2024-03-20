package pe.edu.idat.appborabora.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.idat.appborabora.data.model.response.CategoryResponse
import pe.edu.idat.appborabora.data.network.BoraBoraClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel : ViewModel() {
    private val _category = MutableLiveData<CategoryResponse>()
    val category: LiveData<CategoryResponse> get() = _category

    private val service = BoraBoraClient().getInstance()

    fun fetchCategoryById(categoryId: Int) {
        service.getCategoryById(categoryId).enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                if (response.isSuccessful) {
                    _category.value = response.body()
                } else {
                    // Maneja el error
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                // Maneja el error
            }
        })
    }
}