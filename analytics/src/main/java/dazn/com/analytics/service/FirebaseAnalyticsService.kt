package dazn.com.analytics.service

import android.content.Context
import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class FirebaseAnalyticsService @Inject constructor(context: Context) : AnalyticService {

    private val instance by lazy {
        FirebaseAnalytics.getInstance(context)
    }

    override fun trackEvent(event: String) {
        trackEvent(event.replaceWhiteSpace(), emptyMap())
    }

    override fun trackEvent(event: String, params: Map<String, Any>) {
        instance.logEvent(event.replaceWhiteSpace(), params.toBundle())
    }
}

private fun String.replaceWhiteSpace() = replace(" ", "_")

internal fun Map<String, Any?>.toBundle(): Bundle {
    val transform = this.map {
        it.key.replaceWhiteSpace() to it.value
    }.toTypedArray()
    return bundleOf(*transform)
}
