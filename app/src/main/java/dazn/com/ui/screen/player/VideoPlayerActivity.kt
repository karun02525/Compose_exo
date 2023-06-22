package dazn.com.ui.screen.player

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class VideoPlayerActivity : ComponentActivity() {
    private val videoViewModel: VideoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                VideoPlayerView ()
                showEventCount(videoViewModel)
            }
        }
        val index = intent.getIntExtra(ARG_INDEX, 0)
        videoViewModel.initVideo(index)
        }

    companion object {
        private const val ARG_INDEX = "INDEX"
        fun launch(context: Context, index: Int) {
            context.startActivity(
                Intent(context, VideoPlayerActivity::class.java)
                    .putExtra(ARG_INDEX, index)
            )
        }

    }
}

