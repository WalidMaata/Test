package fr.leboncoin.test.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.leboncoin.test.data.repositories.AlbumRepository
import fr.leboncoin.test.data.repositories.AlbumRepositoryImplem
import fr.leboncoin.test.data.source.AlbumDataSource
import fr.leboncoin.test.data.source.local.AlbumLocalDataSource
import fr.leboncoin.test.data.source.local.TestDataBase
import fr.leboncoin.test.data.source.remote.AlbumRemoteDataSource
import fr.leboncoin.test.data.source.remote.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://static.leboncoin.fr"


    @Singleton
    @Provides
    fun provideApiService(): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): TestDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            TestDataBase::class.java,
            "Articles.db"
        ).build()
    }


    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteAlbumsDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalAlbumsDataSource

    @Singleton
    @RemoteAlbumsDataSource
    @Provides
    fun provideAlbumsRemoteDataSource(
        apiService: ApiService
    ): AlbumDataSource {
        return AlbumRemoteDataSource(apiService)
    }

    @Singleton
    @LocalAlbumsDataSource
    @Provides
    fun provideAlbumsLocalDataSource(
        database: TestDataBase,
    ): AlbumDataSource {
        return AlbumLocalDataSource(
            database.albumDao()
        )
    }
}


/**
 * separate module for Album repository for easy test
 */
@Module
@InstallIn(SingletonComponent::class)
object AlbumsRepositoryModule {

    @Singleton
    @Provides
    fun provideAlbumRepository(
        @AppModule.LocalAlbumsDataSource localAlbumsDataSource: AlbumDataSource,
        @AppModule.RemoteAlbumsDataSource remoteAlbumsDataSource: AlbumDataSource
    ): AlbumRepository {
        return AlbumRepositoryImplem(
            localAlbumsDataSource, remoteAlbumsDataSource
        )
    }
}