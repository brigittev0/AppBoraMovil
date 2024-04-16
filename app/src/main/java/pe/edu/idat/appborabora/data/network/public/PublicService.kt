package pe.edu.idat.appborabora.data.network.public

import okhttp3.ResponseBody
import pe.edu.idat.appborabora.data.dto.request.CreateUserRequest
import pe.edu.idat.appborabora.data.dto.request.LoginRequest
import pe.edu.idat.appborabora.data.dto.request.PasswordUpdateRequest
import pe.edu.idat.appborabora.data.dto.response.ApiResponse
import pe.edu.idat.appborabora.data.dto.response.BrandProductDTO
import pe.edu.idat.appborabora.data.dto.response.CategoryResponse
import pe.edu.idat.appborabora.data.dto.response.CreateUserResponse
import pe.edu.idat.appborabora.data.dto.response.LoginResponse
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.data.dto.response.ProductResponse
import pe.edu.idat.appborabora.data.dto.response.ProductoDashboardResponse
import pe.edu.idat.appborabora.data.dto.response.PurchaseDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PublicService {

    @POST("auth/log-in")
    fun login(@Body data: LoginRequest): Call<LoginResponse>

    @POST("auth/sign-up")
    fun createUser(@Body createUserRequest: CreateUserRequest): Call<CreateUserResponse>

    @POST("user/updatePassword")
    fun updatePassword(@Body passwordUpdateRequest: PasswordUpdateRequest): Call<ApiResponse>

    @GET("categories/all")
    fun getAllCategories(): Call<List<CategoryResponse>>

    @GET("products/topSelling")
    fun getTopSellingProducts(@Query("limit") limit: Int = 10): Call<List<ProductoDashboardResponse>>

    @GET("brand/all")
    fun getAllBrandProducts(): Call<List<BrandProductDTO>>


    @GET("products/byCategory/{categoryId}")
    fun getProductsByCategoryId(@Path("categoryId") categoryId: Int): Call<List<ProductResponse>>

    @GET("products/productResponse/{id}")
    fun getProductById(@Path("id") id: Int): Call<ProductResponse>

}