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
import pe.edu.idat.appborabora.data.dto.response.BrandProductDTO
import pe.edu.idat.appborabora.data.dto.response.CategoryResponse

class ProductViewModel : ViewModel() {

    private val boraBoraClient = BoraBoraClient()

    private val _productCreated = MutableLiveData<Boolean>()
    val productCreated: LiveData<Boolean> get() = _productCreated

    private val _categories = MutableLiveData<List<CategoryResponse>>()
    val categories: LiveData<List<CategoryResponse>> get() = _categories

    private val _brandProducts = MutableLiveData<List<BrandProductDTO>>()
    val brandProducts: LiveData<List<BrandProductDTO>> get() = _brandProducts

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

    fun fetchAllCategories() {
        // Aquí va tu código para obtener todas las categorías y asignarlas a _categories
    }

    fun fetchAllBrandProducts() {
        // Aquí va tu código para obtener todas las marcas de productos y asignarlas a _brandProducts
    }
}


