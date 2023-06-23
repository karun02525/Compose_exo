package dazn.com.data.repositories

import dazn.com.domain.model.PlayListModel
import dazn.com.network.common.Either


interface VideoPlayerRepository {
    suspend fun getVideoPlayer(): Either<List<PlayListModel>>
}
