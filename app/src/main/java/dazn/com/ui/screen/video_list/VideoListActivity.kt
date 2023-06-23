package dazn.com.ui.screen.video_list

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dagger.hilt.android.AndroidEntryPoint
import dazn.com.domain.model.PlayListModel
import dazn.com.network.common.ResponseStatus
import dazn.com.ui.screen.video_player.VideoPlayerActivity
import dazn.com.ui.theme.DaznPlaybackAppTheme
import dazn.com.utils.toast

@AndroidEntryPoint
class VideoListActivity : ComponentActivity() {
    private val videoViewModel: VideoListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DaznPlaybackAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val screenState = videoViewModel.videoList.collectAsState()
                    when (screenState.value) {
                        is ResponseStatus.Error -> {
                            toast(screenState.value.message ?: "")
                        }
                        is ResponseStatus.Loading -> {
                            screenState.value.status?.let { Loading(it) }
                        }
                        is ResponseStatus.Success -> {
                            ListScreen { index ->
                                VideoPlayerActivity.launch(
                                    this@VideoListActivity,
                                    videoViewModel.videoList.value.data as ArrayList<PlayListModel>,
                                    index
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Loading(isLoading: Boolean) {
    if (isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(color = Color.Red)
        }
    }
}
