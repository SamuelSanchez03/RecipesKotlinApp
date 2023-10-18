package com.example.recipesapp.di

import android.content.Context
import com.example.recipesapp.R
import com.example.recipesapp.domain.api.ApiKey
import com.example.recipesapp.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
	private const val BASE_URL = "https://api.spoonacular.com/recipes/"
	
	//RetrofitBuilder for making API calls from the ApiInterface class
	@Singleton
	@Provides
	fun provideRetrofit(): ApiInterface = Retrofit.Builder()
		.addConverterFactory(GsonConverterFactory.create())
		.baseUrl(BASE_URL)
		.build()
		.create(ApiInterface::class.java)
	
	@Singleton
	@Provides
	fun provideApiKey(@ApplicationContext context: Context) =
		ApiKey(key = context.getString(R.string.api_key))
}