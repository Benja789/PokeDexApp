package com.pokedex.app

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.pokedex.app.data.PokemonRetrofit
import com.pokedex.app.data.PokemonX
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PokemonDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pokemon_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getDetails()
    }

    fun getDetails() {
        // Aquí se obtendrán los detalles del pokemon
        var urlPokemon = intent.getStringExtra("pokemon")
        if (urlPokemon != null) {
            val service = PokemonRetrofit.getOnePokemon()
            var idArryas = urlPokemon.split("/")
            var id = idArryas[idArryas.size - 2].toInt()
            service.getPokemon(id).enqueue(object : Callback<PokemonX> {
                override fun onResponse(call: Call<PokemonX>, response: Response<PokemonX>) {
                    if (response.isSuccessful) {
                        val pokemon = response.body()
                        if (pokemon != null) {
                            var imageView: ImageView = findViewById(R.id.imgPokemon)
                            Glide.with(this@PokemonDetails)
                                .load(pokemon.sprites.front_default)
                                .into(imageView)
                            Log.d("PokemonDetails", "Pokemon encontrado: ${pokemon?.sprites?.front_default}")
                        } else {
                            Log.d("PokemonDetails", "No se ha encontrado el pokemon")
                        }
                    }
                }

                override fun onFailure(call: Call<PokemonX>, t: Throwable) {
                    Log.d("PokemonDetails", "Error al obtener el pokemon")
                }
            })
        } else {
            Log.d("PokemonDetails", "No se ha encontrado el pokemon")
        }
    }

    fun backBtn() {
        finish()
    }

}