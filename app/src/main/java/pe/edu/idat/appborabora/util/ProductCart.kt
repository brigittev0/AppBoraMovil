package pe.edu.idat.appborabora.util

import pe.edu.idat.appborabora.data.dto.response.ProductDTO

data class ProductCart(
    val producto: ProductDTO,
    var cantidad: Int
)