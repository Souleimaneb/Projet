package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showList();
        MakeApiCall();
        
            }

    private void showList() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }// define an adapter
        mAdapter = new ListAdapter(input);
        recyclerView.setAdapter(mAdapter);
    }


            private void MakeApiCall(){
               Gson gson = new GsonBuilder()
                       .setLenient()
                       .create();

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
                           List<Film> filmList = response.body().getResults();
                           Toast.makeText(getApplicationContext(), "API Success", Toast.LENGTH_SHORT).show();

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

    private void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

}

