package dazn.com.network.service
import dazn.com.network.common.Api
import dazn.com.network.entity.product.ApiVideoItem
import retrofit2.Response
import retrofit2.http.*

interface VideoPlayerApiService {
    @GET(Api.Url.VIDEO_PLAY_LIST)
    suspend fun getVideoPlayer(): Response<List<ApiVideoItem>>
}
