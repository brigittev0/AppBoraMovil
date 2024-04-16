package pe.edu.idat.appborabora.data.dto.request


data class PurchaseRequest(
    val total: Double,
    val igv: Double,
    val subtotal: Double,
    val purchaseDate: String,
    val user: User,
    val payment: Payment,
    val order: Order,
    val purchaseProducts: List<PurchaseProduct>
)

data class User(val identityDoc: Int)

data class Payment(
    val amount: Double,
    val card: String,
    val currency: String,
    val quota_number: String,
    val trace_number: String,
    val transactionDate: String,
    val card_type: CardType,
    val status: Status
)

data class CardType(val cod_card_type: Int)

data class Status(val codigo_status: Int)

data class Order(
    val orderType: String,
    val type: String,
    val date: String,
    val headquarter: Headquarter? = null,
    val address: String? = null,
    val department: String? = null,
    val district: District? = null,
    val province: String? = null,
    val ubigeo: Int? = null
)

data class Headquarter(val cod_headquarter: Int)

data class District(val cod_district: Int)

data class PurchaseProduct(val product: Product, val quantity: Int)

data class Product(val id_product: Int)