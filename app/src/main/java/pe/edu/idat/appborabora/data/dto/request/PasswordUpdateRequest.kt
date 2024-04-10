package pe.edu.idat.appborabora.data.dto.request

class PasswordUpdateRequest (
    val email: String,
    val cellphone: Int,
    val identityDoc: Int,
    val newPassword: String
)