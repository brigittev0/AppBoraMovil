package pe.edu.idat.appborabora.data.dto.response

import java.time.LocalDate

data class ProductDTO (
    val idProduct: Int = 0,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val expirationDate: LocalDate,
    val image: String,
    val categoryId: Int,
    val brandProductId: Int
)