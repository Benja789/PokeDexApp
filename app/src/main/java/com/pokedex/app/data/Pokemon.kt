package com.pokedex.app.data

import retrofit2.Call
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    // Parte estatica de la petición
    @GET("/api/v2/pokemon/")
    fun listPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<PokemonResult>

    @GET("/api/v2/pokemon/{id}")
    fun getPokemon(@Path("id") id: Int): Call<PokemonX>

}

object PokemonRetrofit {
     fun getAllsPokemon(): PokemonService {
         return Retrofit.Builder()
             .baseUrl("https://pokeapi.co")
             .addConverterFactory(GsonConverterFactory.create())
             .build()
             .create(PokemonService::class.java)
     }
    fun getOnePokemon ():PokemonService {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonService::class.java)
    }
}
