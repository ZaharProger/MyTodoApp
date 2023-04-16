package com.example.mytodoapp.services

import com.example.mytodoapp.entities.http.LanguageGetResponse
import com.example.mytodoapp.entities.http.api.LanguagesApi
import com.example.mytodoapp.entities.http.TranslationResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebClient() {
    private lateinit var executor: Retrofit
    private lateinit var languagesApi: LanguagesApi

    companion object {
        private var webClientInstance: WebClient? = null

        fun getInstance(host: String): WebClient {
            return webClientInstance ?: synchronized(this) {
                val executorInstance = Retrofit.Builder()
                    .baseUrl(host)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val newInstance = WebClient(executorInstance)
                webClientInstance = newInstance
                newInstance
            }
        }
    }

    private constructor(executor: Retrofit) : this() {
        this.executor = executor
        languagesApi = executor.create(LanguagesApi::class.java)
    }

    suspend fun getLanguages(): Response<LanguageGetResponse> {
        return languagesApi.getLanguages()
    }

    suspend fun translate(data: Map<String, String>) : Response<TranslationResponse> {
       return languagesApi.translate(data)
    }
}