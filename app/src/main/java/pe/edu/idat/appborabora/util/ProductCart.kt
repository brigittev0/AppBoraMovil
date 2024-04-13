package pe.edu.idat.appborabora.util

import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import kotlin.math.round

data class ProductCart(
    val producto: ProductDTO,
    var cantidad: Int
) {
    val subtotal: Double
        get() = round(cantidad * producto.price * 100) / 100

    val igv: Double
        get() = round(subtotal * 0.18 * 100) / 100

    val total: Double
        get() = round((subtotal + igv + shipping) * 100) / 100

    var shipping: Double = 0.0
}