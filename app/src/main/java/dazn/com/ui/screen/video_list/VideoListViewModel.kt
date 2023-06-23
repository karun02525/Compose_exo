package dazn.com.ui.screen.video_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dazn.com.domain.model.PlayListModel
import dazn.com.domain.usecase.VideoPlayerUseCase
import dazn.com.network.common.ResponseStatus
import dazn.com.network.common.onFailure
import dazn.com.network.common.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val useCase: VideoPlayerUseCase
) : ViewModel() {

    private val _videoList = MutableStateFlow<ResponseStatus<List<PlayListModel>>>(ResponseStatus.Loading(true))
    val videoList = _videoList.asStateFlow()


    init {
        getVideoPlayer()
    }
    private fun getVideoPlayer() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                useCase.getVideoPlayer().onSuccess {
                    _videoList.emit(ResponseStatus.Loading(false))
                    _videoList.emit(ResponseStatus.Success(it))
                }.onFailure {
                    _videoList.emit(ResponseStatus.Loading(false))
                    _videoList.emit(ResponseStatus.Error(it.message))
                }
            }
        }
    }
}