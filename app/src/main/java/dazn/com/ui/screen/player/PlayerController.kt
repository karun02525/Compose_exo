package dazn.com.ui.screen.player

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import dazn.com.R
import dazn.com.ui.theme.Purple200
import dazn.com.utils.formatMinSec


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,
    isVisible: () -> Boolean,
    isPlaying: () -> Boolean,
    title: () -> String,
    index: () -> Int,
    onPlayPrevious: () -> Unit,
    onPlayNext: () -> Unit,
    onReplayClick: () -> Unit,
    onForwardClick: () -> Unit,
    onPauseToggle: () -> Unit,
    totalDuration: () -> Long,
    currentTime: () -> Long,
    bufferedPercentage: () -> Int,
    playbackState: () -> Int,
    onSeekChanged: (timeMs: Float) -> Unit,
    showNextButton: Boolean,
    showPreviousButton: Boolean
) {

    val visible = remember(isVisible()) { isVisible() }

    AnimatedVisibility(
        modifier = modifier, visible = visible, enter = fadeIn(), exit = fadeOut()
    ) {
        Box(modifier = Modifier.background(Color.Black.copy(alpha = 0.6f))) {
            VideoTitle(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                title = title,
                index = index
            )

            CenterControls(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                isPlaying = isPlaying,
                onPlayPrevious = onPlayPrevious,
                onPlayNext = onPlayNext,
                onReplayClick = onReplayClick,
                onForwardClick = onForwardClick,
                onPauseToggle = onPauseToggle,
                playbackState = playbackState,
                showNextButton = showNextButton,
                showPreviousButton = showPreviousButton,
            )

            SeekbarControls(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .animateEnterExit(enter = slideInVertically(initialOffsetY = { fullHeight: Int ->
                        fullHeight
                    }), exit = slideOutVertically(targetOffsetY = { fullHeight: Int ->
                        fullHeight
                    })),
                totalDuration = totalDuration,
                currentTime = currentTime,
                bufferedPercentage = bufferedPercentage,
                onSeekChanged = onSeekChanged
            )
        }
    }
}


@Composable
private fun CenterControls(
    modifier: Modifier = Modifier,
    isPlaying: () -> Boolean,
    playbackState: () -> Int,
    onPlayPrevious: () -> Unit,
    onPlayNext: () -> Unit,
    onReplayClick: () -> Unit,
    onPauseToggle: () -> Unit,
    onForwardClick: () -> Unit,
    showNextButton: Boolean,
    showPreviousButton: Boolean
) {
    val isVideoPlaying = remember(isPlaying()) { isPlaying() }

    val playerState = remember(playbackState()) { playbackState() }

    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly) {

        if (showPreviousButton) {
            IconButton(modifier = Modifier.size(40.dp), onClick = onPlayPrevious) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.ic_skip_previous),
                    contentDescription = "Replay 5 seconds"
                )
            }
        } else {
            IconButton(modifier = Modifier.size(40.dp), onClick = {}) {

            }
        }

        IconButton(modifier = Modifier.size(40.dp), onClick = onReplayClick) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.ic_replay_5),
                contentDescription = "Replay 5 seconds"
            )
        }

        IconButton(modifier = Modifier.size(40.dp), onClick = onPauseToggle) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = when {
                    isVideoPlaying -> {
                        painterResource(id = R.drawable.ic_pause)
                    }
                    isVideoPlaying.not() && playerState == Player.STATE_ENDED -> {
                        painterResource(id = R.drawable.ic_replay)
                    }
                    else -> {
                        painterResource(id = R.drawable.ic_play)
                    }
                },
                contentDescription = "Play/Pause"
            )
        }

        IconButton(modifier = Modifier.size(40.dp), onClick = onForwardClick) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.ic_forward_10),
                contentDescription = "Forward 10 seconds"
            )
        }

        if (showNextButton) {
            IconButton(modifier = Modifier.size(40.dp), onClick = onPlayNext) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.ic_skip_next),
                    contentDescription = "Forward 10 seconds"
                )
            }
        } else {
            IconButton(modifier = Modifier.size(40.dp), onClick = {}) {

            }
        }
    }
}




@Composable
private fun SeekbarControls(
    modifier: Modifier = Modifier,
    totalDuration: () -> Long,
    currentTime: () -> Long,
    bufferedPercentage: () -> Int,
    onSeekChanged: (timeMs: Float) -> Unit
) {

    val duration = remember(totalDuration()) { totalDuration() }

    val videoTime = remember(currentTime()) { currentTime() }

    val buffer = remember(bufferedPercentage()) { bufferedPercentage() }

    Column(modifier = modifier.padding(bottom = 32.dp)) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Slider(
                value = buffer.toFloat(),
                enabled = false,
                onValueChange = { /*do nothing*/ },
                valueRange = 0f..100f,
                colors = SliderDefaults.colors(
                    disabledThumbColor = Color.Transparent, disabledActiveTrackColor = Color.Gray
                )
            )

            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = videoTime.toFloat(),
                onValueChange = onSeekChanged,
                valueRange = 0f..duration.toFloat(),
                colors = SliderDefaults.colors(
                    thumbColor = Purple200, activeTickColor = Purple200
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = duration.formatMinSec(),
                color = Purple200
            )
        }

    }
}