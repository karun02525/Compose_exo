package dazn.com.network.service

import dazn.com.network.common.Either
import dazn.com.network.entity.product.ApiVideoItem
import dazn.com.network.error.ApiErrorParser
import javax.inject.Inject


class VideoPlayerNetworkService @Inject constructor(
    private val videoPlayerApiService: VideoPlayerApiService,
    private val apiErrorParser: ApiErrorParser
) {

    suspend fun getVideoPlayer(): Either<List<ApiVideoItem>> {
        val response = videoPlayerApiService.getVideoPlayer()
        val body = response.body()
        if (!response.isSuccessful || body == null) {
            val error = apiErrorParser.extractError(response)
            return Either.failure(error)
        }
        return Either.success(body)
    }
}
