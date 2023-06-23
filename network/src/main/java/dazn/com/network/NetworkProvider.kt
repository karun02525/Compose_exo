package dazn.com.network
import com.google.gson.Gson
import dazn.com.network.service.VideoPlayerApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkProvider(
    private val httpClientBuilder: HttpClientBuilder,
    private val gson: Gson
) {
    fun provideProductApiService(): VideoPlayerApiService {
         return provideApiService(BuildConfig.BASE_URL, VideoPlayerApiService::class.java)
    }

    private fun <T> provideApiService(baseUrl: String, service: Class<T>): T {
        return provideRetrofitBuilder(httpClientBuilder.provideOkHttpClient())
            .baseUrl(baseUrl)
            .build()
            .create(service)
    }

    private fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
    }

}
