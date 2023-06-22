package dazn.com.ui.screen.player

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dazn.com.data.VideoRepository
import dazn.com.data.model.Video
import dazn.com.utils.AnalyticsEventLogger
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
    private val analyticsEventLogger: AnalyticsEventLogger
    ) : ViewModel() {

    val pauseCount = mutableStateOf(0)
    val forwardCount = mutableStateOf(0)
    val backwardCount = mutableStateOf(0)

    private val videoList: MutableState<List<Video>?> = mutableStateOf(null)

    var videoIndex = mutableStateOf(0)

    var currentVideoToPlay: Video? =null


    init {
        viewModelScope.launch {
            videoList.value = videoRepository.loadVideos()
        }
    }

    fun initVideo(index: Int) = viewModelScope.launch {
        val videos = videoList.value
        if (videos.isNullOrEmpty()) {
            return@launch
        }
        videoIndex.value = index
        Log.e("VMV", "index = $videoIndex")

        if (videoIndex.value in videos.indices) {
            currentVideoToPlay = videos[videoIndex.value]
            logVideoPlayEvents()
            return@launch
        }
    }

    fun playNextVideo(): Boolean {
        val videos = videoList.value

        if (videos.isNullOrEmpty()) {
            return false
        }

        if(videoIndex.value < videos.size-1) {
            videoIndex.value+=1

            if (videoIndex.value in videos.indices) {
                currentVideoToPlay = videos[videoIndex.value]
                logVideoPlayEvents()
                return true
            }
        }

        return false
    }

    fun playPreviousVideo(): Boolean {
        val videos = videoList.value

        Log.e("TAGS","playPreviousVideo: $videos, $videoIndex")

        if (videos.isNullOrEmpty()) {
            return false
        }

        if (videoIndex.value > 0) {
            videoIndex.value-=1

            if (videoIndex.value in videos.indices) {
                currentVideoToPlay = videos[videoIndex.value]
                logVideoPlayEvents()
                return true
            }

        }

        return false
    }

    private fun logVideoPlayEvents() {
        currentVideoToPlay?.let {
            logVideoPlayEvent(it.name)
            logActionEvent(AnalyticsEventLogger.ACTION_PLAY_VIDEO)
        }
    }

    fun isNextVideoAvailable(): Boolean = videoIndex.value < ((videoList.value?.size ?: 0) - 1)

    fun isPreviousVideoAvailable(): Boolean = videoIndex.value > 0

    fun logActionEvent(action: String) = viewModelScope.launch {
        analyticsEventLogger.logVideoAction(action)
    }

    private fun logVideoPlayEvent(name: String) = viewModelScope.launch {
        analyticsEventLogger.logVideoSelectEvent(name)
    }


}