package com.area.freecture.api

import android.content.Context

import com.google.gson.JsonArray
import com.area.freecture.listeners.ResponseListener
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiRequests(private val context: Context, private val listener: ResponseListener) {
    private var retrofitApi: Retrofit? = null
    private fun getCurrentData(): Retrofit? {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
                    .header("Content-Type", "application/json")
                    .build()
                return chain.proceed(newRequest)
            }
        })

        val logging = HttpLoggingInterceptor()
        logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }

        httpClient.addInterceptor(logging)
        httpClient.readTimeout(30, TimeUnit.SECONDS)

        if (retrofitApi == null) {
            retrofitApi = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        }
        return retrofitApi
    }

    fun getApiRequestMethodArray(pageNb: Int) {
        val apiInterface = getCurrentData()!!.create(ApiInterface::class.java)

        val call = apiInterface.getApiRequestsArray(Constants.UNSPLASH_CLIENT_ID, pageNb)
        call.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, resp: Response<JsonArray>) {
                if (resp.body() != null) {
                    listener.onSuccess(resp.body()!!.toString())
                } else {
                    listener.onError(resp.message())
                }
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                t.printStackTrace()
                listener.onError(t.message ?: "")
            }
        })
    }
}
