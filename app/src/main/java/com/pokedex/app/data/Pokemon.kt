package com.pokedex.app.data

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonService {
    // Parte estatica de la petición
    @GET("/api/v2/pokemon/")
    fun listPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<PokemonResult>
}

object PokemonRetrofit {
     fun makeService(): PokemonService {
         return Retrofit.Builder()
             .baseUrl("https://pokeapi.co")
             .addConverterFactory(GsonConverterFactory.create())
             .build()
             .create(PokemonService::class.java)
     }
}