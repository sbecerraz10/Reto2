package com.example.reto2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;




import java.util.ArrayList;



public class PokeAdapter extends RecyclerView.Adapter<PokeViewModel> {
    private ArrayList<Pokemon> pokemons;
    private OnPokemonClickListener listener;
    public PokeAdapter(){
        pokemons = new ArrayList<>();

    }

    public void addPokemon(Pokemon pokemon){
        pokemons.add(pokemon);
        notifyDataSetChanged();
    }

    public void clear(){
        pokemons.clear();
        notifyDataSetChanged();
    }

    @Override
    public PokeViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.pokemonrow,parent,false);
        PokeViewModel pokemonViewModel = new PokeViewModel(view);

        return pokemonViewModel;
    }

    @Override
    public void onBindViewHolder(@NonNull PokeViewModel holder, int position) {
        Pokemon pokemon = pokemons.get(position);
        holder.getNameRow().setText(pokemon.getName());
        holder.getActionRow().setOnClickListener(
                v->{
                    listener.onPokemonClick(pokemon);
                }
        );
        Glide.with(holder.getImageRow()).load(pokemon.getSprites().getFront_default()).into(holder.getImageRow());

    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public void setListener(OnPokemonClickListener onPokemonClickListener){
        this.listener = onPokemonClickListener;
    }

    public interface OnPokemonClickListener{
        void onPokemonClick(Pokemon pokemon);
    }
}
