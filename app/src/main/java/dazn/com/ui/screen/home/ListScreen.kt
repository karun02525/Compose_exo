package dazn.com.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dazn.com.R
import dazn.com.domain.model.PlayListModel
import dazn.com.ui.screen.main.MainViewModel


@Composable
fun ListScreen(clickedItem: (Int) -> Unit) {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val videos = remember { mainViewModel.videoList }
    videos.value?.let {
        Scaffold { innerPadding ->
            LazyColumn(contentPadding = innerPadding) {
                item {
                    Header(txt = "Video Play List ")
                }
                itemsIndexed(it) { index, video ->
                    Surface(modifier = Modifier.clickable { clickedItem(index) }) {
                        VideoListItem(video)
                        Divider(
                            thickness = 1.dp,
                            color = Color.Gray.copy(0.3f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VideoListItem(data: PlayListModel) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)

    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "",
            modifier = Modifier.clip(MaterialTheme.shapes.small)
        )
        Column {
            Text(text = data.name, style = MaterialTheme.typography.h6)
            Text(
                text = data.uri, style = MaterialTheme.typography.body2,
                color = Color.Blue.copy(.5f)
            )
        }

    }
}

@Composable
fun Header(txt: String, modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colors.onSurface.copy(.1f),
        contentColor = MaterialTheme.colors.primary,
        modifier = modifier.semantics { heading() }) {
        Text(
            text = txt, style = MaterialTheme.typography.subtitle2,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}