package com.myprojects.repository.di

import com.myprojects.data.MyRepo
import com.myprojects.repository.BuildConfig
import com.myprojects.repository.repo.AcronymApi
import com.myprojects.repository.repo.MyRepoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
abstract class RepoModule {

    @Binds
    abstract fun provideGifRepo(gifRepoImpl: MyRepoImpl): MyRepo

    companion object{
        @Provides
        fun provideGifRepoApi(): AcronymApi = getRetrofit().create(AcronymApi::class.java)

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder().baseUrl(BuildConfig.ACRONYM_END_POINT)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
    }
}