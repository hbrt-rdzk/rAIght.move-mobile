package com.raightmove.raightmove.repositories

import com.raightmove.raightmove.models.AnalysisRequestBody
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class ExplainerRepository {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val client = OkHttpClient()
    private val explainURL = "https://raight-move-backend-tfj4plw3kq-lm.a.run.app/api/v1/explain/"

    suspend fun fetchFeedback(analysisData: AnalysisRequestBody): String {
        val jsonAdapter = moshi.adapter(AnalysisRequestBody::class.java)
        val json = jsonAdapter.toJson(analysisData)

        val jsonMediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val requestBody = json.toRequestBody(jsonMediaType)
        val request = Request.Builder()
            .url(explainURL)
            .post(requestBody)
            .addHeader("Content-Type", "application/json")
            .build()

        return withContext(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                response.body?.string() ?: "No Response Body"
            } catch (e: IOException) {
                e.printStackTrace()
                "Request failed: ${e.message}"
            }
        }
    }
}