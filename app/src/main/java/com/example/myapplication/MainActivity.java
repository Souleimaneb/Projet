package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://swapi.dev";

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("application_esiea", Context.MODE_PRIVATE);
        gson = new GsonBuilder()
                .setLenient()
                .create();

        List<Film> filmList = getDataFromCache();

        if(filmList != null ) {
            showList(filmList);
        } else {
            MakeApiCall();
                }



            }

    private List<Film> getDataFromCache() {
        String jsonFilm = sharedPreferences.getString(Constants.KEY_FILM_LIST, null);
        if (jsonFilm == null) {
            return null;
        } else {

            Type listType = new TypeToken<List<Film>>() {}.getType();
        return gson.fromJson(jsonFilm, listType);
    }

}

    private void showList(List<Film> filmList) {
        Toast.makeText(getApplicationContext(), "In showList", Toast.LENGTH_SHORT).show();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(filmList);
        recyclerView.setAdapter(mAdapter);
    }


            private void MakeApiCall(){
               Retrofit retrofit = new Retrofit.Builder()
                       .baseUrl(BASE_URL)
                       .addConverterFactory(GsonConverterFactory.create(gson))
                       .build();

               SwApi swApi = retrofit.create(SwApi.class);

               Call<RestFilmResponse> call = swApi.getFilmResponse();
               call.enqueue(new Callback<RestFilmResponse>() {
                   @Override
                   public void onResponse(Call<RestFilmResponse> call, Response<RestFilmResponse> response) {
                       if(response.isSuccessful() && response.body() != null){
                           Toast.makeText(getApplicationContext(), "AAAAAH", Toast.LENGTH_SHORT).show();
                           List<Film> filmList = response.body().getResults();
                           saveList(filmList);
                            showList(filmList);
                       } else {
                             //  showError();
                           Toast.makeText(getApplicationContext(), "API Error2", Toast.LENGTH_SHORT).show();
                       }
                   }

                   @Override
                   public void onFailure(Call<RestFilmResponse> call, Throwable t) {
                      // showError();
                       Toast.makeText(getApplicationContext(), "API Error1", Toast.LENGTH_SHORT).show();

                   }
               });
           }

    private void saveList(List<Film> filmList) {
        String jsonString = gson.toJson(filmList);

        sharedPreferences
                .edit()
                .putString( Constants.KEY_FILM_LIST, jsonString )
                .apply();

        Toast.makeText(getApplicationContext(), "Liste save", Toast.LENGTH_SHORT).show();

    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

}

