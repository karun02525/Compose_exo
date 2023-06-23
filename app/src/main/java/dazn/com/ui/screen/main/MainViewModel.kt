package dazn.com.ui.screen.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dazn.com.domain.model.PlayListModel
import dazn.com.domain.usecase.VideoPlayerUseCase
import dazn.com.network.common.onFailure
import dazn.com.network.common.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: VideoPlayerUseCase) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading get() = _isLoading.asStateFlow()
    val videoList: MutableState<List<PlayListModel>?> = mutableStateOf(null)

    init {
        viewModelScope.launch {
           useCase.getVideoPlayer().onSuccess {
               _isLoading.value = false
               withContext(Dispatchers.Main) {
                   videoList.value = it
               }
           }.onFailure {
               _isLoading.value = false
           }
        }
    }
}