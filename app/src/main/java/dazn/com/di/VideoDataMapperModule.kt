package dazn.com.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dazn.com.data.mappers.VideoPlayerResponseMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VideoDataMapperModule {
    @Provides
    @Singleton
    fun provideVideoResponseMapper() : VideoPlayerResponseMapper = VideoPlayerResponseMapper()

}