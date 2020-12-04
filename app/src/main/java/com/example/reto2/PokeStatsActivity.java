package com.example.reto2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.bumptech.glide.Glide;

import java.util.List;

public class PokeStatsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button dropPokemonBtn;
    private ImageView pokeImg;
    private TextView pokeNameTV;
    private TextView pokeDefenseTV;
    private TextView pokeAttackTV;
    private TextView pokeSpeedTV;
    private TextView pokeHpTV;
    private TextView pokeTypeTV;

    private User user;
    private Pokemon poke;

    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokestats);
        user = (User)getIntent().getExtras().getSerializable("user");
        poke = (Pokemon) getIntent().getExtras().getSerializable("pokemon");

        dropPokemonBtn = findViewById(R.id.dropPokemonBtn);
        pokeImg = findViewById(R.id.pokeImg);
        pokeNameTV = findViewById(R.id.pokeNameTV);
        pokeDefenseTV = findViewById(R.id.pokeDefenseTV);
        pokeAttackTV = findViewById(R.id.pokeAttackTV);
        pokeSpeedTV = findViewById(R.id.pokeSpeedTV);
        pokeHpTV = findViewById(R.id.pokeHpTV);
        pokeTypeTV= findViewById(R.id.pokeTypeTV);




        pokeNameTV.setText(poke.getName());
        pokeDefenseTV.setText(poke.getStats().get(2).getBase_stat());
        pokeAttackTV.setText(poke.getStats().get(1).getBase_stat());
        pokeSpeedTV.setText(poke.getStats().get(5).getBase_stat());
        pokeHpTV.setText(poke.getStats().get(0).getBase_stat());
        database = FirebaseFirestore.getInstance();
        String type = "(";
        List<PokeType> types = poke.getTypes();
        boolean pos = false;
        for (PokeType ty:types) {
            if(!pos){
                type +=ty.getType().getName();
                pos = true;
            }else{
                type +=" "+ty.getType().getName();
            }

        }
        type +=")";
        pokeTypeTV.setText(type.replace(" ",","));
        dropPokemonBtn.setOnClickListener(this);
        Glide.with(pokeImg).load(poke.getSprites().getFront_default()).into(pokeImg);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dropPokemonBtn:
                database.collection("pokemons").document(user.getId()).collection("catch").document(poke.getName()).delete();
                finish();
                break;
        }
    }
}
