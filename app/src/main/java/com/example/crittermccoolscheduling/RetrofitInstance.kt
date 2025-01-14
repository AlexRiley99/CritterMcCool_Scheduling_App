import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private val gson = GsonBuilder()
        .setLenient()  // Allow lenient JSON parsing
        .create()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2/CritterMcCool_App/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
