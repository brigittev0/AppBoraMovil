package pe.edu.idat.appborabora.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.data.network.BoraBoraClient
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class ProductViewModel : ViewModel() {

    private val boraBoraClient = BoraBoraClient()

    private val _productCreated = MutableLiveData<Boolean>()
    val productCreated: LiveData<Boolean> get() = _productCreated

    fun createProduct(context: Context, productDTO: ProductDTO) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("ProductViewModel", "Datos del producto: $productDTO")
                val response = boraBoraClient.getInstanceWithAuth(context).createProduct(productDTO).execute()
                if (response.isSuccessful) {
                    _productCreated.postValue(true)
                } else {
                    _productCreated.postValue(false)
                    Log.e("ProductViewModel", "Error al crear el producto: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error al crear el producto: ${e.message}")
                _productCreated.postValue(false)
            }
        }
    }
}


