package dazn.com.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dazn.com.utils.AnalyticsEventLogger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GoogleAnalyticsModule {

    @Provides
    @Singleton
    fun provideAnalyticsEventLogger(@ApplicationContext appContext: Context): AnalyticsEventLogger = AnalyticsEventLogger()

}