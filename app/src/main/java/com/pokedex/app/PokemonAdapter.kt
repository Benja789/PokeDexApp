package com.pokedex.app
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pokedex.app.data.PokemonItem

class PokemonAdapter(
    private val pokemonList: List<PokemonItem>,
    private val onItemClick: (PokemonItem) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokemonName: TextView = view.findViewById(R.id.pokemon_name)
        val cardView: CardView = view.findViewById(R.id.card_pokemon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.pokemonName.text = pokemon.name.capitalize()
        holder.cardView.setOnClickListener {
            onItemClick(pokemon)
        }
    }

    override fun getItemCount() = pokemonList.size
}