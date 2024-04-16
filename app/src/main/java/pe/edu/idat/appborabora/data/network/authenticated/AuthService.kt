package pe.edu.idat.appborabora.data.network.authenticated

import pe.edu.idat.appborabora.data.dto.request.PurchaseRequest
import pe.edu.idat.appborabora.data.dto.request.UpdateUserRequest
import pe.edu.idat.appborabora.data.dto.response.ApiResponse
import pe.edu.idat.appborabora.data.dto.response.UserProfileResponse
import pe.edu.idat.appborabora.data.dto.response.ProductDTO
import pe.edu.idat.appborabora.data.dto.response.ProductoEmptyRequest
import pe.edu.idat.appborabora.data.dto.response.PurchaseDTO
import pe.edu.idat.appborabora.data.dto.response.PurchasetResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AuthService {

    @GET("user/findUser/{username}")
    fun getUserByUsername(@Path("username") username: String): Call<UserProfileResponse>

    @PUT("user/updateUser/{identityDoc}")
    fun updateUser(@Path("identityDoc") identityDoc: Int, @Body updateUserRequest: UpdateUserRequest): Call<ApiResponse>

    @POST("products/createProduct")
    fun createProduct(@Body productDTO: ProductDTO): Call<ApiResponse>

    @GET("products/all")
    fun getAllProducts(): Call<List<ProductDTO>>

    @GET("products/{productId}")
    fun getProductById(@Path("productId") productId: Int): Call<ProductoEmptyRequest>

    @DELETE("products/delete/{id}")
    fun deleteProduct(@Path("id") id: Int): Call<Void>

    @PUT("products/update/{id}")
    fun updateProduct(@Path("id") id: Int, @Body productDTO: ProductDTO): Call<ApiResponse>


    @GET("purchase/all/{identityDoc}")
    fun getAllPurchases(@Path("identityDoc") identityDoc: Int): Call<List<PurchasetResponse>>

    @POST("purchase/PICKUP")
    fun createPickUpOrder(@Body purchaseRequest: PurchaseRequest): Call<ApiResponse>

    @POST("purchase/DELIVERY")
    fun createDeliveryOrder(@Body purchaseRequest: PurchaseRequest): Call<ApiResponse>

    @GET("purchase/{purchase_id}")
    fun getPurchaseById(@Path("purchase_id") purchase_id: Int): Call<PurchasetResponse>

}