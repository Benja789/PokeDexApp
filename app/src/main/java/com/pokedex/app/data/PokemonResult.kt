package com.pokedex.app.data

data class PokemonResult(
    var results: List<PokemonItem>,
    var count: Number?,
    var next: String?,
    var previous: String?
)

data class  PokemonItem(
    var name: String,
    var url: String
)
