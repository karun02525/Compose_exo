package dazn.com.analytics.service


interface AnalyticService {

    fun trackEvent(event: String)

    fun trackEvent(event: String, params: Map<String, Any>)

}