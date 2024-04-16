package pe.edu.idat.appborabora.data.dto.response

// ProductoDashboardResponse.kt
data class ProductoDashboardResponse(
    val id_product: Int,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val expirationDate: String,
    val image: String,
    val categoryId: Int,
    val categoryName: String,
    val brandProductId: Int,
    val brandProductName: String
)