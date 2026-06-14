package com.example.cadeachadosperdidos.api

import com.example.cadeachadosperdidos.model.Objeto
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("objetos")
    fun listarObjetos(): Call<List<Objeto>>

    @GET("objetos/{id}")
    fun buscarObjetoPorId(@Path("id") id: Int): Call<Objeto>

    @POST("objetos")
    fun criarObjeto(@Body objeto: Objeto): Call<Objeto>

    @PUT("objetos/{id}")
    fun atualizarObjeto(
        @Path("id") id: Int,
        @Body objeto: Objeto
    ): Call<Objeto>

    @DELETE("objetos/{id}")
    fun deletarObjeto(@Path("id") id: Int): Call<Void>
}