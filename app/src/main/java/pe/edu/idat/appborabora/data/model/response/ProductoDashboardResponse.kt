package pe.edu.idat.appborabora.data.model.response

// ProductoDashboardResponse.kt
data class ProductoDashboardResponse(
    val idProduct: Int,
    val name: String,
    val description: String,
    val brand: String,
    val price: Double,
    val stock: Int,
    val expirationDate: String,
    val image: String,
    val categoryId: Int,
    val categoryName: String,
    val brandProductId: Int,
    val brandProductName: String
)