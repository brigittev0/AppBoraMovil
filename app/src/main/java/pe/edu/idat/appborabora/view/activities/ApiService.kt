package pe.edu.idat.appborabora.view.activities
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService{
    @POST("AprobarSolicitud")
    fun AprobarSolicitud(@Body datos: DatosEnviar?): Call<Respuesta?>?
}
