package pe.edu.idat.appborabora.data.dto.response

data class PerfilResponse(

    val identityDoc: Int,
    val name: String,
    val lastname: String,
    val cellphone: Int,
    val email: String,
    val username: String
)