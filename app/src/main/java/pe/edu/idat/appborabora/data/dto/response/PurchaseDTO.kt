package pe.edu.idat.appborabora.data.dto.response

import java.time.LocalDate

data class PurchaseDTO(
    val total: Double,
    val purchaseDate: String,
    val paymentId: Int,
    val orderId: Int,
)