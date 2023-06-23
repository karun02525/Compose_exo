package dazn.com.ui.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import dazn.com.domain.model.PlayListModel
import dazn.com.ui.screen.home.ListScreen
import dazn.com.ui.screen.player.VideoPlayerActivity
import dazn.com.ui.screen.player.VideoViewModel
import dazn.com.ui.theme.DaznPlaybackAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val videoViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DaznPlaybackAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ListScreen { index ->
                        VideoPlayerActivity.launch(this,
                            videoViewModel.videoList.value as ArrayList<PlayListModel>, index)
                    }
                }
            }
        }
    }
}
