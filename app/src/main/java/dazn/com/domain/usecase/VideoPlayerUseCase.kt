package dazn.com.domain.usecase

import dazn.com.domain.model.PlayListModel
import dazn.com.network.common.Either


interface VideoPlayerUseCase {
    suspend fun getVideoPlayer(): Either<List<PlayListModel>>
}