package pe.edu.idat.appborabora.util

data class ProductPurchase(
    val id_product: Int,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val expirationDate: String,
    val image: String,
    val categoryId: Int,
    val brandProductId: Int
)
