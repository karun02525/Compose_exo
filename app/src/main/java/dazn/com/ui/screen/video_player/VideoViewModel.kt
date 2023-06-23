package dazn.com.ui.screen.video_player

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dazn.com.analytics.Analytic.Event.VIDEO_PLAY
import dazn.com.analytics.Analytic.Key.PLAY_VIDEO_NAME
import dazn.com.analytics.service.AnalyticService
import dazn.com.domain.model.PlayListModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    val analyticService: AnalyticService) : ViewModel() {

    val pauseCount = mutableStateOf(0)
    val forwardCount = mutableStateOf(0)
    val backwardCount = mutableStateOf(0)

    private val videoList: MutableState<List<PlayListModel>?> = mutableStateOf(null)

    var videoIndex = mutableStateOf(0)

    var currentVideoToPlay: PlayListModel? =null



    fun initVideo(data:List<PlayListModel>,index: Int) = viewModelScope.launch {
        videoList.value = data
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
            val params = hashMapOf(PLAY_VIDEO_NAME to it.name)
            analyticService.trackEvent(VIDEO_PLAY,params)
        }
    }

    fun isNextVideoAvailable(): Boolean = videoIndex.value < ((videoList.value?.size ?: 0) - 1)

    fun isPreviousVideoAvailable(): Boolean = videoIndex.value > 0


}