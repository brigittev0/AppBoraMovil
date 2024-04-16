package pe.edu.idat.appborabora.data.dto.response

import java.time.LocalDate


data class PurchasetResponse(
    val purchase_id: Int,
    val total: Double,
    val igv: Double,
    val subtotal: Double,
    val purchaseDate: String,
    val paymentId: Int,
    val orderType: String,
    val identityDoc: Int,
    val productIds: List<Int>
)