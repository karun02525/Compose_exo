package dazn.com.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dazn.com.data.mappers.VideoPlayerResponseMapper
import dazn.com.data.repositories.VideoPlayerRepository
import dazn.com.data.repositories.VideoPlayerRepositoryImpl
import dazn.com.domain.usecase.VideoPlayerUseCase
import dazn.com.domain.usecase.VideoPlayerUseCaseImpl
import dazn.com.network.service.VideoPlayerNetworkService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VideoDomainModule {

    @Provides
    @Singleton
    fun provideRepositoryImpl(videoPlayerNetworkService: VideoPlayerNetworkService, videoPlayerResponseMapper: VideoPlayerResponseMapper
    ): VideoPlayerRepository {
        return VideoPlayerRepositoryImpl(videoPlayerNetworkService, videoPlayerResponseMapper)
    }

    @Provides
    @Singleton
    fun provideUseCaseImpl(repository: VideoPlayerRepository): VideoPlayerUseCase {
        return VideoPlayerUseCaseImpl(repository)
    }


}