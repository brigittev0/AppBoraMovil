package pe.edu.idat.appborabora.data.dto.response

data class LoginResponse(
    val username: String,
    val message: String,
    val jwt: String,
    val status: Boolean,
    val roles: List<String>,
    val identityDoc: Int
)
