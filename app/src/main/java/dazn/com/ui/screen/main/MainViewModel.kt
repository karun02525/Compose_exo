package dazn.com.ui.screen.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dazn.com.data.VideoRepository
import dazn.com.data.model.Video
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val videoRepository: VideoRepository) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)

    val isLoading get() = _isLoading.asStateFlow()

    val videoList: MutableState<List<Video>?> = mutableStateOf(null)

    //to check internet connection
    val isInternetAvailable = mutableStateOf(false)

    init {
        viewModelScope.launch {
            //load video list from json file in assets
            videoList.value = videoRepository.loadVideos()
            //delay to show default splash screen
            delay(500)
            _isLoading.value = false
        }
    }
}