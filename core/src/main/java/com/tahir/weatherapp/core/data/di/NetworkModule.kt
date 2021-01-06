package com.tahir.weatherapp.core.data.di

import com.google.gson.Gson
import com.tahir.weatherapp.core.BuildConfig
import com.tahir.weatherapp.core.data.network.Webservices
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Module to inject Retrofit2 related dependencies.
 */
@Module
class NetworkModule {

    /**
     * Provide Gson object to create GsonConverterFactory
     */
    @Singleton
    @Provides
    fun providesGson(): Gson {
        return Gson()
    }


    /**
     * Provide Converter Factory to convert Json to our model objects
     */
    @Singleton
    @Provides
    fun providesConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }



    /**
     * Provide OkHttpClient to initialize Retrofit instance
     */
    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {

        val builder = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)

        //this is just for debug purpose to see the logs.
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }
        return builder.build()
    }


    /**
     * Provide Retrofit object to create Webservice object which is used to call api endpoints
     */
    @Singleton
    @Provides
    fun providesRetrofit2(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    /**
     * Create an implementation of the Webservices API endpoints. It will be used to call
     * apis
     */
    @Singleton
    @Provides
    fun providesWebservices(retrofit: Retrofit): Webservices {
        return retrofit.create(Webservices::class.java)
    }


}