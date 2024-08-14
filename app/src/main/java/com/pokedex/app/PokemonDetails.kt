package com.pokedex.app

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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

                            // Seteo de la informacion de los pokemons
                            var imageView: ImageView = findViewById(R.id.imgPokemon)
                            Glide.with(this@PokemonDetails)
                                .load(pokemon.sprites.front_default)
                                .into(imageView)
                            var textViewTitle: TextView = findViewById(R.id.lblName)
                            textViewTitle.text = pokemon.name.capitalize()

                            // Atributos de los pokemons
                            for ( i in 0 until pokemon.stats.size) {

                                val attributeId = resources.getIdentifier("attribute_${i + 1}", "id", packageName)
                                val textView: TextView = findViewById(attributeId)
                                textView.text = "${pokemon.stats[i].stat.name.capitalize()}"
                                val valueId = resources.getIdentifier("value_${i + 1}", "id", packageName)
                                val valueView: TextView = findViewById(valueId)
                                valueView.text = "${pokemon.stats[i].base_stat}"

                            }

                        } else {
                            Toast.makeText(
                                this@PokemonDetails,
                                "No se ha encontrado el pokemon",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }

                override fun onFailure(call: Call<PokemonX>, t: Throwable) {
                    Toast.makeText(
                        this@PokemonDetails,
                        "Error al obtener los datos del pokemon",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } else {
            Toast.makeText(this, "No se ha encontrado el pokemon", Toast.LENGTH_SHORT).show()
        }
    }
    fun backBtn(view: View) {
        finish()
    }

}