package dazn.com.data

import android.content.Context
import dazn.com.data.model.Video
import dazn.com.utils.DataConvertor


class VideoRepository(private val context: Context) {

    fun loadVideos(): List<Video> {
        val json = context.assets.open("videos.json").bufferedReader().use { it.readText() }
        return DataConvertor.toVideList(json)
    }

    fun loadTestVideos(): ArrayList<Video> {
        val list = ArrayList<Video>()
        list.add(
            Video(
                "test name",
                "test url"
        ))

        return list
    }

}
