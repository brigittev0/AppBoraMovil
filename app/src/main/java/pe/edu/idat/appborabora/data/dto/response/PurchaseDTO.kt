package pe.edu.idat.appborabora.data.dto.response

import java.time.LocalDate

data class PurchaseDTO(
    val orderId: Int,
    val purchaseDate: String,
    val paymentId: Int,
    val total: Double,
    val compraId: Int,
    val igv: Double,
    val subtotal: Double,
    val identityDoc: Int,
    val productIds: List<Int>
)