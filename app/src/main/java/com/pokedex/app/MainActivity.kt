package com.pokedex.app

import RepeatedTask
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.pokedex.app.data.PokemonItem
import com.pokedex.app.data.PokemonResult
import com.pokedex.app.data.PokemonRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter
    private var pokemonList = mutableListOf<PokemonItem>()
    private var pokemonCopyList = mutableListOf<PokemonItem>()
    private var receiver: NetworkChangeReceiver? = null
    private lateinit var task: RepeatedTask

    private var pokemonTotal = 15
    private val timeRefreshApp:Long = 30000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.container_pokemon)
        recyclerView.layoutManager = LinearLayoutManager(this)
        pokemonAdapter = PokemonAdapter(pokemonList) { item -> onPokemonClick(item) }
        recyclerView.adapter = pokemonAdapter
        receiver = NetworkChangeReceiver(this)


        val inputText: TextInputEditText = findViewById(R.id.input_txt_search)
        inputText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                task.stop()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Handle text changes here
                if ( s.toString().isEmpty() ) {
                    pokemonList.clear()
                    pokemonList.addAll(pokemonCopyList)
                    pokemonAdapter.notifyDataSetChanged()
                    task.start()
                } else {
                    val filterList = pokemonCopyList.filter { it.name.contains(s.toString(), ignoreCase = true) }
                    pokemonList.clear()
                    pokemonList.addAll(filterList)
                    pokemonAdapter.notifyDataSetChanged()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(receiver, filter)

        task = RepeatedTask(timeRefreshApp)
        task.executeTask = {
            getDataInitial()
        }
        task.start()
    }

    // Metodo para obtener el listado de pokemons
    private fun getDataInitial() {
        val service = PokemonRetrofit.getAllsPokemon()
        service.listPokemon(pokemonTotal, 0).enqueue(object : Callback<PokemonResult> {
            override fun onResponse(call: Call<PokemonResult>, response: Response<PokemonResult>) {
                if (response.isSuccessful) {
                    val pokemonResult = response.body()
                    pokemonResult?.let {
                        pokemonList.addAll(it.results)
                        pokemonAdapter.notifyDataSetChanged()
                    }
                    pokemonCopyList = pokemonList
                    if ( pokemonTotal > 15 ) {
                        Toast.makeText(this@MainActivity, "Se han cargado ${pokemonTotal} pokemons", Toast.LENGTH_SHORT).show()
                    }
                    pokemonTotal += 10
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<PokemonResult>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: No se logro obtener el listado de pokemons", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun onPokemonClick(pokemon: PokemonItem) {
        var intent = Intent(this, PokemonDetails::class.java)
        intent.putExtra("pokemon", pokemon.url)
        task.stop()
        startActivity(intent)
    }
}