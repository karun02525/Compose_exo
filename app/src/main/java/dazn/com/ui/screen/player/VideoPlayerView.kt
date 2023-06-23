package dazn.com.ui.screen.player

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import dazn.com.R
import dazn.com.analytics.Analytic
import dazn.com.analytics.Analytic.Event.SWIPE_NEXT
import dazn.com.analytics.Analytic.Event.SWIPE_PREV
import dazn.com.analytics.Analytic.Event.VIDEO_PLAY_RESUME_VIDEO
import dazn.com.analytics.Analytic.Event.VIDEO_PLAY_SCREEN
import dazn.com.domain.model.PlayListModel
import dazn.com.utils.toast


private const val PLAYER_SEEK_BACK_INCREMENT = 5 * 1000L
private const val PLAYER_SEEK_FORWARD_INCREMENT = 10 * 1000L

@Composable
@UnstableApi
@androidx.annotation.OptIn(UnstableApi::class)
fun VideoPlayerView() {

    val videoViewModel = hiltViewModel<VideoViewModel>()
    val context = LocalContext.current
    val video = remember { mutableStateOf(videoViewModel.currentVideoToPlay) }

    val exoPlayer = remember {
        intiExoplayer(context, video.value!!)
    }


    exoPlayer.playWhenReady = true
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    exoPlayer.repeatMode = Player.REPEAT_MODE_OFF


    val shouldShowControls = remember { mutableStateOf(false) }
    val isPlaying = remember { mutableStateOf(exoPlayer.isPlaying) }
    val totalDuration = remember { mutableStateOf(0L) }
    val currentTime = remember { mutableStateOf(0L) }
    val bufferedPercentage = remember { mutableStateOf(0) }
    val playbackState = remember { mutableStateOf(exoPlayer.playbackState) }
    val isBuffering = remember { mutableStateOf(true) }


    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val latestLifecycleEvent = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            latestLifecycleEvent.value = event
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    when (latestLifecycleEvent.value) {
        Lifecycle.Event.ON_RESUME -> {
            exoPlayer.playWhenReady = true
        }
        Lifecycle.Event.ON_PAUSE -> {
            exoPlayer.playWhenReady = false
        }
        else -> {

        }
    }

    val listener = object : Player.Listener {
        override fun onEvents(
            player: Player, events: Player.Events
        ) {
            super.onEvents(player, events)
            totalDuration.value = player.duration.coerceAtLeast(0L)
            currentTime.value = player.currentPosition.coerceAtLeast(0L)
            bufferedPercentage.value = player.bufferedPercentage
            isPlaying.value = player.isPlaying
            playbackState.value = player.playbackState
            when (player.playbackState) {
                Player.STATE_BUFFERING -> {
                    isBuffering.value = true
                }
                Player.STATE_READY -> {
                    isBuffering.value = false
                }
                Player.STATE_ENDED -> {
                    playNextVideo(context, videoViewModel, exoPlayer, video, shouldShowControls)
                }
            }
        }
    }

    val direction = remember { mutableStateOf(0f) }
    Box(modifier = Modifier
        .height(400.dp)
        .pointerInput(Unit) {
            detectHorizontalDragGestures(onHorizontalDrag = { _, dragAmount ->
                direction.value = dragAmount
            }, onDragEnd = {
                if (direction.value > 0) {
                    videoViewModel.analyticService.trackEvent(SWIPE_PREV)
                    playPreviousVideo(
                        context, videoViewModel, exoPlayer, video, shouldShowControls
                    )
                } else {
                    videoViewModel.analyticService.trackEvent(SWIPE_NEXT)
                    playNextVideo(
                        context, videoViewModel, exoPlayer, video, shouldShowControls
                    )
                }
            })
        }) {
        DisposableEffect(key1 = Unit) {
            exoPlayer.addListener(listener)
            onDispose {
                exoPlayer.removeListener(listener)
                exoPlayer.release()
            }
        }
        AndroidView(modifier = Modifier
            .height(400.dp)
            .clickable {
                shouldShowControls.value = shouldShowControls.value.not()
            }, factory = {
            PlayerView(context).apply {
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                player = exoPlayer
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        })

        PlayerControls(
            modifier = Modifier.fillMaxSize(),
            isVisible = { shouldShowControls.value },
            isPlaying = { isPlaying.value },
            title = { video.value?.name ?: "----" },
            index = { videoViewModel.videoIndex.value },

            onPlayNext = {
                videoViewModel.forwardCount.value+=1
                val params = hashMapOf(Analytic.Key.PLAY_NEXT to videoViewModel.forwardCount.value)
                videoViewModel.analyticService.trackEvent(VIDEO_PLAY_SCREEN, params)
                playNextVideo(context, videoViewModel, exoPlayer, video, shouldShowControls)
            },
            onPlayPrevious = {
                videoViewModel.backwardCount.value+=1
                val params = hashMapOf(Analytic.Key.PREVIOUS to videoViewModel.backwardCount.value)
                videoViewModel.analyticService.trackEvent(VIDEO_PLAY_SCREEN, params)
                playPreviousVideo(context, videoViewModel, exoPlayer, video, shouldShowControls)
            },

            playbackState = { playbackState.value },
            onReplayClick = {
                exoPlayer.seekBack()
            },
            onForwardClick = {
                exoPlayer.seekForward()
            },
            onPauseToggle = {
                when {
                    exoPlayer.isPlaying -> {
                        // pause the video
                        videoViewModel.pauseCount.value+=1
                        val params = hashMapOf(Analytic.Key.PAUSE_VIDEO to videoViewModel.pauseCount.value)
                        videoViewModel.analyticService.trackEvent(VIDEO_PLAY_SCREEN, params)
                        exoPlayer.pause()
                    }
                    else -> {
                        videoViewModel.analyticService.trackEvent(VIDEO_PLAY_RESUME_VIDEO)
                        exoPlayer.play()
                    }
                }
                isPlaying.value = isPlaying.value.not()
            },
            totalDuration = { totalDuration.value },
            currentTime = { currentTime.value},
            bufferedPercentage = { bufferedPercentage.value },
            onSeekChanged = { timeMs: Float ->
                exoPlayer.seekTo(timeMs.toLong())
            },
            showNextButton = videoViewModel.isNextVideoAvailable(),
            showPreviousButton = videoViewModel.isPreviousVideoAvailable(),
        )
        if (isBuffering.value) {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = Color.Yellow)
            }
        }


    }
}


fun playNextVideo(
    context: Context,
    videoViewModel: VideoViewModel,
    exoPlayer: ExoPlayer,
    video: MutableState<PlayListModel?>,
    shouldShowControls: MutableState<Boolean>
) {
    shouldShowControls.value = false
    if (videoViewModel.playNextVideo()) {
        exoPlayer.playWhenReady = false
        video.value = videoViewModel.currentVideoToPlay!!
        playAnotherVideo(exoPlayer, videoViewModel.currentVideoToPlay)
    } else {
        context.toast(context.getString(R.string.no_next_video_to_play))
    }
}

fun playPreviousVideo(
    context: Context,
    videoViewModel: VideoViewModel,
    exoPlayer: ExoPlayer,
    video: MutableState<PlayListModel?>,
    shouldShowControls: MutableState<Boolean>
) {
    shouldShowControls.value = false
    if (videoViewModel.playPreviousVideo()) {
        exoPlayer.playWhenReady = false
        video.value = videoViewModel.currentVideoToPlay
        playAnotherVideo(exoPlayer, videoViewModel.currentVideoToPlay)
    } else {
        context.toast(context.getString(R.string.no_previous_video_to_play))
    }
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun intiExoplayer(context: Context, video: PlayListModel): ExoPlayer {
    val player = ExoPlayer.Builder(context).apply {
        setSeekBackIncrementMs(PLAYER_SEEK_BACK_INCREMENT)
        setSeekForwardIncrementMs(PLAYER_SEEK_FORWARD_INCREMENT)
    }.build().apply {
        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
        val source = DashMediaSource.Factory(defaultHttpDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(video.uri))
        setMediaSource(source)
        prepare()
    }
    return player
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun playAnotherVideo(exoPlayer: ExoPlayer, video: PlayListModel?) {
    if (video == null) {
        return
    }
    val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
    val source = DashMediaSource.Factory(defaultHttpDataSourceFactory)
        .createMediaSource(MediaItem.fromUri(video.uri))
    exoPlayer.setMediaSource(source)
    exoPlayer.prepare()
    exoPlayer.playWhenReady = true
}



@Composable
fun VideoTitle(
    modifier: Modifier = Modifier,
    title: () -> String,
    index: () -> Int,
) {
    val videoTitle = remember(title()) { title() }
    val videoIndex = remember(index()) { index() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            modifier = modifier.padding(10.dp),
            text = "${videoIndex + 1}. $videoTitle",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Yellow,
            textAlign = TextAlign.Start
        )
    }


}

@Composable
fun showEventCount(viewModel: VideoViewModel) {
    Column(
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Pause count: ${viewModel.pauseCount.value}",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Forward count: ${viewModel.forwardCount.value}",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Backward count: ${viewModel.backwardCount.value}",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            fontSize = 16.sp
        )
    }

}
