package pe.edu.idat.appborabora.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.idat.appborabora.data.dto.response.BrandProductDTO
import pe.edu.idat.appborabora.data.network.public.PublicClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class BrandProductViewModel : ViewModel() {
    private val _brandProducts = MutableLiveData<List<BrandProductDTO>>()
    val brandProducts: LiveData<List<BrandProductDTO>> get() = _brandProducts

    private val service = PublicClient().getInstance()

    fun fetchAllBrandProducts() {
        service.getAllBrandProducts().enqueue(object : Callback<List<BrandProductDTO>> {
            override fun onResponse(call: Call<List<BrandProductDTO>>, response: Response<List<BrandProductDTO>>) {
                if (response.isSuccessful) {
                    _brandProducts.value = response.body()
                } else {
                    // Manejar el error
                }
            }

            override fun onFailure(call: Call<List<BrandProductDTO>>, t: Throwable) {
                // Manejar el error
            }
        })
    }
}
