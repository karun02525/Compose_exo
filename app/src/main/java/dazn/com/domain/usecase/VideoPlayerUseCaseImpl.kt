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
       //test for local data
        //return getLocalData()
    }

    private fun getLocalData(): Either<List<PlayListModel>> {
        val list = mutableListOf<PlayListModel>()
        list.run {
            add(PlayListModel("HD (MP4, H264)", "https://storage.googleapis.com/wvmedia/clear/h264/tears/tears.mpd"))
            add(PlayListModel("HD (MP4, H265)", "https://storage.googleapis.com/wvmedia/clear/hevc/tears/tears.mpd"))
            add(PlayListModel("UHD (MP4, H265)", "https://storage.googleapis.com/wvmedia/clear/hevc/tears/tears_uhd.mpd"))
        }
        return Either.success(list)
    }
}