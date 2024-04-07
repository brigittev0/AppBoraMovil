package pe.edu.idat.appborabora.data.network.service

import pe.edu.idat.appborabora.data.dto.request.CreateUserRequest
import pe.edu.idat.appborabora.data.dto.response.ApiResponse
import pe.edu.idat.appborabora.data.dto.response.BrandProductDTO
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AuthenticatedService {

    @PUT("user/updateUser/{identityDoc}")
    fun updateUser(@Path("identityDoc") identityDoc: Int, @Body createUserRequest: CreateUserRequest): Call<ApiResponse>

    @POST("products/createProduct")
    fun createProduct(@Body productDTO: ProductDTO): Call<ProductDTO>

    @GET("brand/all")
    fun getAllBrandProducts(): Call<List<BrandProductDTO>>
}