package pe.edu.idat.appborabora.data.network.authenticated

import pe.edu.idat.appborabora.data.dto.request.CreateUserRequest
import pe.edu.idat.appborabora.data.dto.response.ApiResponse
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    /*
    @PUT("user/updateUser/{identityDoc}")
    fun updateUser(@Path("identityDoc") identityDoc: Int, @Body createUserRequest: CreateUserRequest): Call<ApiResponse>
    */

    @POST("products/createProduct")
    fun createProduct(@Body productDTO: ProductDTO): Call<ApiResponse>

    @GET("products/all")
    fun getAllProducts(): Call<List<ProductDTO>>
}