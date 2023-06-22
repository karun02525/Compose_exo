package dazn.com.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dazn.com.data.VideoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VideoRepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(@ApplicationContext appContext: Context): VideoRepository = VideoRepository(appContext)

}