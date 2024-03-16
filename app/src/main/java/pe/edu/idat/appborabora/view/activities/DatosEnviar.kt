package pe.edu.idat.appborabora.view.activities

class DatosEnviar {
    private var Usuario: String = ""
    private var Contraseña: String = ""

    fun getUsuario(): String {
        return Usuario
    }

    fun setUsuario(Usuario: String) {
        this.Usuario = Usuario
    }

    fun getContraseña(): String {
        return Contraseña
    }

    fun setContraseña(Contraseña: String) {
        this.Contraseña = Contraseña
    }
}
