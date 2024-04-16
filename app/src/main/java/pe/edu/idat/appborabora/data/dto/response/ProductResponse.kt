package pe.edu.idat.appborabora.data.dto.response

data class ProductResponse(
    val id_product: Int,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val expirationDate: String,
    val image: String,

    val categoryName: String,
    val brandProductName: String,
    val deleted: Boolean,
    val categoryId: Int,
    val brandProductId: Int,
)