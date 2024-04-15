package pe.edu.idat.appborabora.data.dto.response

import java.time.LocalDate

data class ProductDTO (
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val expirationDate: String,
    val image: String,
    val categoryId: Int,
    val brandProductId: Int


)