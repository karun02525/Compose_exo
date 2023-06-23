package dazn.com.analytics.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dazn.com.analytics.service.AnalyticService
import dazn.com.analytics.service.FirebaseAnalyticsService
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AnalyticDataModule {
    @Provides
    @Singleton
    fun provideFirebaseAnalyticsService(@ApplicationContext appContext: Context): AnalyticService {
        return FirebaseAnalyticsService(appContext)
    }

}