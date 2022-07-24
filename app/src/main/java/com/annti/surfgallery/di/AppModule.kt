package com.annti.surfgallery.di

import com.annti.surfgallery.data.network.AuthApi
import com.annti.surfgallery.data.network.GalleryApi
import com.annti.surfgallery.data.network.NetInterceptor
import com.annti.surfgallery.data.network.RetrofitFactory
import com.annti.surfgallery.data.repository.*
import com.annti.surfgallery.domain.*
import com.annti.surfgallery.presentation.auth.LoginViewModel
import com.annti.surfgallery.presentation.favorite.FavoriteViewModel
import com.annti.surfgallery.presentation.home.GalleryViewModel
import com.annti.surfgallery.utils.MoshiInstantAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://pictures.chronicker.fun/"

val appModule = module {

    single {
        OkHttpClient().newBuilder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(NetInterceptor(get()))
            .build()
    }

    single {
        NetInterceptor(authUseCase = get())
    }

    single {
        RetrofitFactory(okHttpClient = get())
    }

    single {
        val okHttp = OkHttpClient().newBuilder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
        RetrofitFactory(okHttp).makeService<AuthApi>(BASE_URL)
    }

    single {
        get<RetrofitFactory>().makeService<GalleryApi>(BASE_URL)
    }

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(MoshiInstantAdapter())
            .build()
    }

    single<PreferencesRepository> {
        SharedPreferencesRepository(
            context = get()
        )
    }

    single<AuthRepository> {
        AuthRepositoryImpl(
            authApi = get()
        )
    }

    single<SessionRepository> {
        SessionRepositoryImpl(
            preferencesRepository = get(),
            moshi = get()
        )
    }

    single<GalleryRepository> {
        GalleryRepositoryImpl(
            galleryApi = get()
        )
    }

    single<AuthUseCase> {
        AuthUseCaseImpl(
            authRepository = get(),
            sessionRepository = get()
        )
    }

    single<GalleryUseCase> {
        GalleryUseCaseImpl(
            galleryRepository = get()
        )
    }

    viewModel {
        LoginViewModel(
            authUseCase = get()
        )
    }

    viewModel {
        GalleryViewModel(
            galleryUseCase = get()
        )
    }

    viewModel {
        FavoriteViewModel(
            galleryUseCase = get()
        )
    }

}