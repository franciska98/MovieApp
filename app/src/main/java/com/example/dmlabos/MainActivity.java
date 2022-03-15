package com.example.dmlabos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ArrayList<String> titles;
    ArrayList<String> posters;
    RecyclerView recyclerView;
    AdapterMovies adapterMovies;

    public static String BASE_URL = "https://api.themoviedb.org";
    public static String CATEGORY = "popular";
    public static int PAGE = 1;
    public static String API_KEY = "728a879e314a07234865ad399133489c";
    public static String LANGUAGE = "en-US";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        titles = new ArrayList<>();
        posters = new ArrayList<>();
        titles.clear();
        posters.clear();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface myInterface = retrofit.create(ApiInterface.class);

        Call<MoviesResult> call = myInterface.listOfMovies(CATEGORY, API_KEY, LANGUAGE, PAGE);

        call.enqueue(new Callback<MoviesResult>() {
            @Override
            public void onResponse(Call<MoviesResult> call, Response<MoviesResult> response) {
                MoviesResult results = response.body();
                List<MoviesResult.Result> listOfMovies = results.getResults();

                for (int i = 0; i < listOfMovies.size(); i++) {
                    titles.add(listOfMovies.get(i).getTitle());
                    posters.add(listOfMovies.get(i).getPosterPath());
                }
                adapterMovies = new AdapterMovies(MainActivity.this, titles, posters);
                recyclerView.setAdapter(adapterMovies);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            }

            @Override
            public void onFailure(Call<MoviesResult> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                logout();
                return true;
            case R.id.menuSearch:
                startActivity(new Intent(MainActivity.this,SearchMovieActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void logout(){
        mAuth.signOut();
        finish();
    }

}