package pe.edu.idat.appborabora.data.model.response

data class LoginResponse(
    val username: String,
    val message: String,
    val jwt: String,
    val status: Boolean,
    val roles: List<String>
)
