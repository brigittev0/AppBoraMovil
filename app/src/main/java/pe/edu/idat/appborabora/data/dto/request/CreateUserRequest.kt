package pe.edu.idat.appborabora.data.dto.request

class CreateUserRequest(
    val identity_doc: Int,
    val name: String,
    val lastname: String,
    val cellphone: Int,
    val email: String,
    val username: String,
    val password: String,
    val roles: Set<String>
)