package com.example.dmlabos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchMovieActivity extends AppCompatActivity {

    EditText movieTitleEditText;
    Button searchButton;
    TextView movieTitleTaxtView;
    ImageView posterImageView;
    TextView runtimeTextView;
    TextView yearTextView;
    TextView genreTextView;
    TextView actorTextView;
    TextView plotTextView;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        movieTitleEditText=findViewById(R.id.movieTitleEditText);
        searchButton=findViewById(R.id.searchButton);
        movieTitleTaxtView=findViewById(R.id.movieTitleTextView);
        posterImageView=findViewById(R.id.posterImageView);
        runtimeTextView=findViewById(R.id.runtimeTextView);
        yearTextView=findViewById(R.id.yearTextView);
        genreTextView=findViewById(R.id.genreTextView);
        actorTextView=findViewById(R.id.actorTextView);
        plotTextView=findViewById(R.id.plotTextView);
    }

    public void searchMovie(View view) {
        requestQueue= Volley.newRequestQueue(this);
        String name=movieTitleEditText.getText().toString();
        String url="https://www.omdbapi.com/?t="+name+"&plot=full&apikey=4af60584";

        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object=new JSONObject(response);
                    String id=object.getString("imdbID");

                    String title=object.getString("Title");
                    movieTitleTaxtView.setText(title);
                    String year=object.getString("Year");
                    yearTextView.setText(year);
                    String runtime=object.getString("Runtime");
                    runtimeTextView.setText(runtime);
                    String genre=object.getString("Genre");
                    genreTextView.setText(genre);
                    String actor=object.getString("Actors");
                    actorTextView.setText(actor);
                    String plot=object.getString("Plot");
                    plotTextView.setText(plot);
                    String poster=object.getString("Poster");
                    posterImageView.setVisibility(View.VISIBLE);
                    Glide.with(SearchMovieActivity.this).load(poster).into(posterImageView);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }
}