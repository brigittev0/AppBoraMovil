package pe.edu.idat.appborabora.util

import pe.edu.idat.appborabora.data.dto.response.ProductDTO

data class ProductCart(
    val producto: ProductDTO,
    var cantidad: Int
) {
    val subtotal: Double
        get() = cantidad * producto.price

    val igv: Double
        get() = subtotal * 0.18

    val total: Double
        get() = subtotal + igv + shipping

    var shipping: Double = 0.0
}