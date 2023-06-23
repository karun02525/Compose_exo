package dazn.com.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class PlayListModel(
    val name: String,
    val uri: String,
): Parcelable

/*

[
{
    "name": "HD (MP4, H264)",
    "uri": "https://storage.googleapis.com/wvmedia/clear/h264/tears/tears.mpd"
},
{
    "name": "UHD (MP4, H264)",
    "uri": "https://storage.googleapis.com/wvmedia/clear/h264/tears/tears_uhd.mpd"
},
{
    "name": "HD (MP4, H265)",
    "uri": "https://storage.googleapis.com/wvmedia/clear/hevc/tears/tears.mpd"
},
{
    "name": "UHD (MP4, H265)",
    "uri": "https://storage.googleapis.com/wvmedia/clear/hevc/tears/tears_uhd.mpd"
},
{
    "name": "HD (WebM, VP9)",
    "uri": "https://storage.googleapis.com/wvmedia/clear/vp9/tears/tears.mpd"
},
{
    "name": "UHD (WebM, VP9)",
    "uri": "https://storage.googleapis.com/wvmedia/clear/vp9/tears/tears_uhd.mpd"
}
]*/
