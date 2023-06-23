package dazn.com.domain.usecase

import dazn.com.data.repositories.VideoPlayerRepository
import dazn.com.domain.model.PlayListModel
import dazn.com.network.common.Either
import javax.inject.Inject


class VideoPlayerUseCaseImpl @Inject constructor(
    private val repository: VideoPlayerRepository
) : VideoPlayerUseCase {
    override suspend fun getVideoPlayer(): Either<List<PlayListModel>> {
        return repository.getVideoPlayer()
    }
}