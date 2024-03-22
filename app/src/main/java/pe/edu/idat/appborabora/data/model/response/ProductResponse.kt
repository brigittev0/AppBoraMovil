package pe.edu.idat.appborabora.data.model.response

import java.time.LocalDate

data class ProductResponse (
    val idProduct: Int,
    val name: String,
    val description: String,
    val brand: String,
    val price: Double,
    val stock: Int,
    val expirationDate: LocalDate,
    val image: String,
    val categoryId: Int,
    val categoryName: String,
    val brandProductId: Int,
    val brandProductName: String,
)