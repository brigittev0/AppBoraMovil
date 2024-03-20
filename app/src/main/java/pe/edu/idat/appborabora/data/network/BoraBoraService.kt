package pe.edu.idat.appborabora.data.network

import pe.edu.idat.appborabora.adapter.ProductoDashboardAdapter
import pe.edu.idat.appborabora.data.model.request.LoginRequest
import pe.edu.idat.appborabora.data.model.response.CategoryResponse
import pe.edu.idat.appborabora.data.model.response.LoginResponse
import pe.edu.idat.appborabora.data.model.response.ProductoDashboardResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BoraBoraService {

    @POST("login")
    fun login(@Body data: LoginRequest): Call<LoginResponse>

    @GET("categories/byId/{categoryId}")
    fun getCategoryById(@Path("categoryId") categoryId: Int): Call<CategoryResponse>

    @GET("products/topSelling")
    fun getTopSellingProducts(@Query("limit") limit: Int = 10): Call<List<ProductoDashboardResponse>>

}