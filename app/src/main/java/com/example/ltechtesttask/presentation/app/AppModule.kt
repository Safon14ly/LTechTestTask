package com.example.ltechtesttask.presentation.app

import com.example.ltechtesttask.data.api.DevExamApi
import com.example.ltechtesttask.data.repository.ElementsRepositoryImpl
import com.example.ltechtesttask.data.repository.LoginRepositoryImpl
import com.example.ltechtesttask.domain.repository.ElementsRepository
import com.example.ltechtesttask.domain.repository.LoginRepository
import com.example.ltechtesttask.domain.useCases.ElementsUseCase
import com.example.ltechtesttask.domain.useCases.LoginUseCase
import com.example.ltechtesttask.presentation.loginFragment.LoginViewModel
import com.example.ltechtesttask.presentation.mainFragment.MainViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {
    fun injectModules() = loadModules

    private val loadModules by lazy {
        loadKoinModules(
            listOf(
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        )
    }

    private val networkModule = module {
        single { provideClient() }
        single { provideRetrofitBuilder(get()) }
        single { provideService(get()) }
    }

    private val repositoryModule = module {
        single<LoginRepository> { LoginRepositoryImpl(get()) }
        single<ElementsRepository> { ElementsRepositoryImpl(get()) }
    }

    private val useCaseModule = module {
        factory { LoginUseCase(get()) }
        factory { ElementsUseCase(get()) }
    }

    private val viewModelModule = module {
        viewModel { LoginViewModel(get()) }
        viewModel { MainViewModel(get()) }
    }

    private fun provideClient() = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .build()

    private fun provideRetrofitBuilder(client: OkHttpClient) = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_API_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    private fun provideService(retrofit: Retrofit): DevExamApi =
        retrofit.create(DevExamApi::class.java)
}