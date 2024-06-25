// AuthService.kt
package com.raightmove.raightmove.services

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

data class LoginRequest(val username: String, val password: String)
data class RegisterRequest(val username: String, val password: String, val email: String)
data class AuthResponse(val token: String, val userId: String)

class AuthService {

    private val client = OkHttpClient()
    private val gson = Gson()
    private val json = "application/json; charset=utf-8".toMediaTypeOrNull()

    @Throws(IOException::class)
    fun login(request: LoginRequest): AuthResponse? {
        val requestBody = gson.toJson(request).toRequestBody(json)
        val httpRequest = Request.Builder()
            .url("https://your-backend-url.com/auth/login")
            .post(requestBody)
            .build()
        client.newCall(httpRequest).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            return gson.fromJson(response.body?.string(), AuthResponse::class.java)
        }
    }

    @Throws(IOException::class)
    fun register(request: RegisterRequest): AuthResponse? {
        val requestBody = gson.toJson(request).toRequestBody(json)
        val httpRequest = Request.Builder()
            .url("https://your-backend-url.com/auth/register")
            .post(requestBody)
            .build()
        client.newCall(httpRequest).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            return gson.fromJson(response.body?.string(), AuthResponse::class.java)
        }
    }
}
