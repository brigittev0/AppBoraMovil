package pe.edu.idat.appborabora.data.network

import pe.edu.idat.appborabora.data.dto.request.CreateUserRequest
import pe.edu.idat.appborabora.data.dto.request.LoginRequest
import pe.edu.idat.appborabora.data.dto.response.ApiResponse
import pe.edu.idat.appborabora.data.dto.response.BrandProductDTO
import pe.edu.idat.appborabora.data.dto.response.CategoryResponse
import pe.edu.idat.appborabora.data.dto.response.LoginResponse
import pe.edu.idat.appborabora.data.dto.response.ProductoDashboardResponse
import pe.edu.idat.appborabora.data.dto.response.PerfilResponse
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface BoraBoraService {

    //-- SIN TOKEN
    @POST("auth/log-in")
    fun login(@Body data: LoginRequest): Call<LoginResponse>

    @GET("categories/all")
    fun getAllCategories(): Call<List<CategoryResponse>>

    @GET("products/topSelling")
    fun getTopSellingProducts(@Query("limit") limit: Int = 10): Call<List<ProductoDashboardResponse>>

    @GET("user/findUser/{username}")
    fun getUserByUsername(@Path("username") username: String): Call<PerfilResponse>

    // -- CON TOKEN
    @PUT("user/updateUser/{identityDoc}")
    fun updateUser(@Path("identityDoc") identityDoc: Int, @Body createUserRequest: CreateUserRequest): Call<ApiResponse>

    @POST("products/createProduct")
    fun createProduct(@Body productDTO: ProductDTO): Call<ProductDTO>

    @GET("brand/all")
    fun getAllBrandProducts(): Call<List<BrandProductDTO>>
}