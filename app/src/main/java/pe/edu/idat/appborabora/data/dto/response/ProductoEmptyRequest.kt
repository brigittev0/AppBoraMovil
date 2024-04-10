package pe.edu.idat.appborabora.data.dto.response

data class ProductoEmptyRequest (
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val expirationDate: String,
    val image: String,
    val categoryId: Int,
    val brandProductId: Int,
    val deleted: Boolean
)