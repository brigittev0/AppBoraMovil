package pe.edu.idat.appborabora.data.model.response

data class RoleResponse(
    val authority: String
)

data class LoginResponse(
    val role: List<RoleResponse>,
    val message: String,
    val token: String,
    val username: String
)
