package com.example.mytodoapp.entities.http.api

import com.example.mytodoapp.entities.http.LanguageGetResponse
import com.example.mytodoapp.entities.http.TranslationResponse
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface LanguagesApi {
    @Headers(
        "X-RapidAPI-Key: 10ac3fdce4msh50faffec00cdb9dp197ee1jsn45101311ea4f",
        "X-RapidAPI-Host: text-translator2.p.rapidapi.com"
    )
    @GET("/getLanguages")
    suspend fun getLanguages(): Response<LanguageGetResponse>

    @Headers(
        "X-RapidAPI-Key: 10ac3fdce4msh50faffec00cdb9dp197ee1jsn45101311ea4f",
        "X-RapidAPI-Host: text-translator2.p.rapidapi.com"
    )
    @FormUrlEncoded
    @POST("/translate")
    suspend fun translate(@FieldMap data: Map<String, String>): Response<TranslationResponse>
}