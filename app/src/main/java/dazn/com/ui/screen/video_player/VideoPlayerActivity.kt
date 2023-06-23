package dazn.com.ui.screen.video_player

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import dagger.hilt.android.AndroidEntryPoint
import dazn.com.domain.model.PlayListModel

@AndroidEntryPoint
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class VideoPlayerActivity : ComponentActivity() {
    private val videoViewModel: VideoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                VideoPlayerView()
                showEventCount(videoViewModel)
            }
        }
        val index = intent.extras?.getInt(ARG_INDEX, 0)?:0
        val data:List<PlayListModel> = intent.extras?.getParcelableArrayList(ARG_DATA)!!
        videoViewModel.initVideo(data, index)
    }

    companion object {
        private const val ARG_INDEX = "index"
        private const val ARG_DATA = "data"
        fun launch(context: Context, data: ArrayList<PlayListModel>, index: Int) {
            val bundle = Bundle().apply {
                putParcelableArrayList(ARG_DATA, data);
                putInt(ARG_INDEX, index)
            }
            context.startActivity(
                Intent(context, VideoPlayerActivity::class.java)
                    .putExtras(bundle)
            )
        }

    }
}

