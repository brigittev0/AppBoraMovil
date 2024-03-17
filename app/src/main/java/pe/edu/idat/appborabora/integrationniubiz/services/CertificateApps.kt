package pe.edu.idat.appborabora.integrationniubiz.services


import pe.edu.idat.appborabora.integrationniubiz.Constants
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface CertificateApps  {

    //API de Certificado para Apps - Niubiz
    @Headers(
        "Accept: application/json"
    )
    @POST(Constants.CERTIFICATE_APP_ENDPOINT + Constants.MERCHANT_ID)
    suspend fun getCertificate(@Header("Authorization") token : String): Response<ResponseBody>

}