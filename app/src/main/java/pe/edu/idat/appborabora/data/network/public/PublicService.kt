package pe.edu.idat.appborabora.data.network.public

import pe.edu.idat.appborabora.data.dto.request.LoginRequest
import pe.edu.idat.appborabora.data.dto.response.BrandProductDTO
import pe.edu.idat.appborabora.data.dto.response.CategoryResponse
import pe.edu.idat.appborabora.data.dto.response.LoginResponse
import pe.edu.idat.appborabora.data.dto.response.ProductoDashboardResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PublicService {

    @POST("auth/log-in")
    fun login(@Body data: LoginRequest): Call<LoginResponse>

    @GET("categories/all")
    fun getAllCategories(): Call<List<CategoryResponse>>

    @GET("products/topSelling")
    fun getTopSellingProducts(@Query("limit") limit: Int = 10): Call<List<ProductoDashboardResponse>>



    @GET("brand/all")
    fun getAllBrandProducts(): Call<List<BrandProductDTO>>
}