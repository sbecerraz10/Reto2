package com.example.reto2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;

import java.util.Date;

public class CatchPokeActivity extends AppCompatActivity implements View.OnClickListener, PokeAdapter.OnPokemonClickListener{

    private EditText catchPokeET;
    private Button catchPokeBtn;
    private EditText searchPokeET;
    private Button searchPokeBtn;
    private RecyclerView recyclerView;

    private User user;
    private Pokemon poke;

    private PokeAdapter adapter;

    private HTTPSWebUtilDomi webUtilDomi;
    private Gson gson;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catchpoke);
        user = (User) getIntent().getExtras().getSerializable("user");
        catchPokeET = findViewById(R.id.catchPokeET);
        catchPokeBtn = findViewById(R.id.catchPokeBtn);
        searchPokeET = findViewById(R.id.searchPokeET);
        searchPokeBtn = findViewById(R.id.searchPokeBtn);
        recyclerView = findViewById(R.id.recyclerView);

        poke = new Pokemon();
        webUtilDomi = new HTTPSWebUtilDomi();
        database = FirebaseFirestore.getInstance();
        gson = new Gson();
        adapter= new PokeAdapter();
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        catchPokeBtn.setOnClickListener(this);
        searchPokeBtn.setOnClickListener(this);



    }


    public void loadPokemonList() {
        Query pokeReference = database.collection("pokemons").document(user.getId()).collection("catch").orderBy("timestamp", Query.Direction.DESCENDING);
        if(pokeReference!=null){
            pokeReference.get().addOnCompleteListener(
                    task -> {
                        adapter.clear();
                        for(QueryDocumentSnapshot doc : task.getResult()){
                            Pokemon pokemon = doc.toObject(Pokemon.class);
                            poke = pokemon;
                            adapter.addPokemon(pokemon);
                        }
                    }
            );
        }

    }

    @Override
    protected void onResume() {
        loadPokemonList();
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.catchPokeBtn:
                new Thread(
                        ()->{
                            String json = webUtilDomi.GETrequest("https://pokeapi.co/api/v2/pokemon/"+catchPokeET.getText().toString().toLowerCase().replace(" ","-"));
                            //Log.e(">>>",catchPokeET.getText()+" Entra");
                            //Log.e(">>>",json);

                            if (json!=null && !json.equals("") && !catchPokeET.getText().toString().equals("")) {
                                Pokemon pokemon = gson.fromJson(json,Pokemon.class);
                                poke = pokemon;
                                pokemon.setTimestamp(new Date().getTime());
                                database.collection("pokemons").document(user.getId()).collection("catch").document(pokemon.getName()).set(pokemon);
                                runOnUiThread( ()->{
                                    Toast.makeText(this,"El pokemon "+ catchPokeET.getText().toString()+"ha sido atrapado",Toast.LENGTH_LONG).show();
                                    catchPokeET.setText("");
                                    loadPokemonList();
                                });
                            }else{
                                runOnUiThread( ()->{
                                    Toast.makeText(this,"El pokemon "+ catchPokeET.getText().toString()+" no existe en la base de datos",Toast.LENGTH_LONG).show();
                                });

                            }
                        }
                ).start();
                break;

            case R.id.searchPokeBtn:
                if (searchPokeET.getText().toString().equals("")){
                    loadPokemonList();
                }else{
                    new Thread(
                            ()->{
                                CollectionReference pokeReference = database.collection("pokemons").document(user.getId()).collection("catch");
                                Query query = pokeReference.whereEqualTo("name",searchPokeET.getText().toString().toLowerCase());
                                query.get().addOnCompleteListener(task -> {
                                    if(task.isSuccessful() && task.getResult().size()>0){
                                        adapter.clear();
                                        for(QueryDocumentSnapshot doc : task.getResult()){
                                            Pokemon pokemon = doc.toObject(Pokemon.class);
                                            adapter.addPokemon(pokemon);
                                            searchPokeET.setText("");
                                        }
                                    }else {
                                        runOnUiThread(()->{Toast.makeText(this,"El pokemon "+searchPokeET.getText().toString()+" no ha sido atrapado o no existe",Toast.LENGTH_LONG).show();});
                                    }
                                });
                            }
                    ).start();
                }
                break;
        }
    }

    @Override
    public void onPokemonClick(Pokemon pokemon) {
        Intent intent = new Intent(this,PokeStatsActivity.class);
        intent.putExtra("pokemon",pokemon);
        intent.putExtra("user",user);
        startActivity(intent);
    }
}

