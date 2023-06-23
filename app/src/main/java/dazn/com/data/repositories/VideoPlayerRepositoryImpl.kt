package dazn.com.data.repositories
import dazn.com.data.mappers.VideoPlayerResponseMapper
import dazn.com.domain.model.PlayListModel
import dazn.com.network.common.Either
import dazn.com.network.common.map
import dazn.com.network.service.VideoPlayerNetworkService
import javax.inject.Inject


class VideoPlayerRepositoryImpl  @Inject constructor (
    private val videoPlayerNetworkService: VideoPlayerNetworkService,
    private val videoPlayerResponseMapper: VideoPlayerResponseMapper,
) : VideoPlayerRepository {
    override suspend fun getVideoPlayer(): Either<List<PlayListModel>> {
        return try {
            videoPlayerNetworkService.getVideoPlayer().map(videoPlayerResponseMapper::toDomain)
        } catch (error: Throwable) {
            Either.failure(error)
        }
    }
}