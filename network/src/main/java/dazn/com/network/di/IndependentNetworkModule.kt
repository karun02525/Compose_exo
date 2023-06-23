package dazn.com.network.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dazn.com.network.HttpClientBuilder
import dazn.com.network.NetworkProvider
import dazn.com.network.error.ApiErrorParser
import dazn.com.network.service.VideoPlayerApiService
import dazn.com.network.service.VideoPlayerNetworkService
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object IndependentNetworkModule {

    @Singleton
    @Provides
    fun providesGson() = Gson()

    @Singleton
    @Provides
    fun providesHttpClientBuilder() = HttpClientBuilder()

    @Singleton
    @Provides
    fun providesApiErrorParser(gson: Gson)= ApiErrorParser(gson)


    @Singleton
    @Provides
    fun providesNetworkProvider(httpClientBuilder: HttpClientBuilder, gson: Gson): NetworkProvider {
        return NetworkProvider(httpClientBuilder, gson)
    }

    @Singleton
    @Provides
    fun providesApiService(httpClientBuilder: HttpClientBuilder, gson: Gson) :VideoPlayerApiService {
       return  providesNetworkProvider(httpClientBuilder, gson).provideProductApiService()
    }

    @Singleton
    @Provides
    fun providesProductNetworkService(
        videoPlayerApiService: VideoPlayerApiService,
        apiErrorParser: ApiErrorParser
    ): VideoPlayerNetworkService {
        return VideoPlayerNetworkService(videoPlayerApiService, apiErrorParser)
    }


}

