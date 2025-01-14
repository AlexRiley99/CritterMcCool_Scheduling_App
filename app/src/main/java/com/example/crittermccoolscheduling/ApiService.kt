import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    //Data class representing an appointment request
    data class AppointmentRequest(
        val id: Int? = null, //Backend handles auto-incrementing
        val fullName: String,
        val phoneNumber: String,
        val emailAddress: String,
        val serviceAddress: String,
        val insectType: String,
        val problemDuration: String,
        val requestedTime: String,
        val requestedDate: String,
        val count: Int //Row count
    )

    //Data class representing an API response - either success or error
    data class ApiResponse(
        val success: Boolean,
        val message: String,
        val data: AppointmentRequest?)

    //Get row count
    @GET("get_row_count.php")
    fun getRowCount(): Call<ApiResponse>

    // Create a new appointment request
    @POST("insert_appointment_request.php")
    fun insertRequest(@Body appointmentRequest: AppointmentRequest): Call<ApiService.ApiResponse>

    // Get a list of all appointment requests
    @GET("get_all_appointment_requests.php")
    fun getAllRequests(): Call<List<AppointmentRequest>>

    // Get a single appointment request by ID
    @GET("get_appointment_request.php")
    fun getRequest(@Query("id") id: Int): Call<AppointmentRequest>

    // Delete a single request by ID
    @DELETE("delete_appointment_request.php")
    fun deleteRequest(@Query("id") id: Int): Call<ApiResponse>

    // Delete all requests
    @DELETE("delete_all_appointment_requests.php")
    fun deleteAllRequests(): Call<ApiResponse>
}
