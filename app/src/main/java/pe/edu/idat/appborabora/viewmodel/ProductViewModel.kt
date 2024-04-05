package pe.edu.idat.appborabora.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.data.network.BoraBoraClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class ProductViewModel: ViewModel() {

    private val _productCreated = MutableLiveData<Boolean>()
    val productCreated: LiveData<Boolean> get() = _productCreated

    private val boraBoraService = BoraBoraClient().getInstance()

    fun createProduct(productDTO: ProductDTO) {
        boraBoraService.createProduct(productDTO).enqueue(object : Callback<ProductDTO> {
            override fun onResponse(call: Call<ProductDTO>, response: Response<ProductDTO>) {
                _productCreated.value = response.isSuccessful
            }

            override fun onFailure(call: Call<ProductDTO>, t: Throwable) {
                _productCreated.value = false
            }
        })
    }
}