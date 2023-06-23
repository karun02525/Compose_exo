package dazn.com.analytics

object Analytic {

    object Event {
        const val VIDEO_PLAY_SCREEN = "video_play_screen"
        const val VIDEO_PLAY_RESUME_VIDEO = "resume_video"
        const val VIDEO_PLAY = "played_video"
        const val SWIPE_NEXT = "swipe_next"
        const val SWIPE_PREV = "swipe_prev"
    }

    object Key {
        const val PAUSE_VIDEO = "pause_video"
        const val PLAY_NEXT = "play_next"
        const val PREVIOUS = "play_previous"
        const val PLAY_VIDEO_NAME = "video_name"
    }
}