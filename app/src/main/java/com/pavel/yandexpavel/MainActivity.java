package com.pavel.yandexpavel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pavel.yandexpavel.model.Artist;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArtistRecyclerViewAdapter adapter;
    List<Artist> artists;
    Call<List<Artist>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        artists = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.artists_list);
        adapter = new ArtistRecyclerViewAdapter(artists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecycleListener(this, new RecycleListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent questionIntent = new Intent(MainActivity.this, ArtistInfo.class);
                questionIntent.putExtra(ArtistInfo.TAG, artists.get(position));
                // startActivity(questionIntent);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        // the context of the activity
                        MainActivity.this,
                        // For each shared element, add to this method a new Pair item,
                        // which contains the reference of the view we are transitioning *from*,
                        // and the value of the transitionName attribute
                        new Pair<View, String>(view.findViewById(R.id.artist_image),
                                getString(R.string.transition_name_BigPhoto)),
                        new Pair<View, String>(view.findViewById(R.id.artist_genres),
                                getString(R.string.transition_name_stules)),
                        new Pair<View, String>(view.findViewById(R.id.artist_stuff),
                                getString(R.string.transition_name_albums))

                );
                ActivityCompat.startActivity(MainActivity.this, questionIntent, options.toBundle());

            }
        }));

        // adapter.


        loadArtists();
    }

    private void loadArtists() {
        list = new Retrofit.Builder()
                .baseUrl("http://download.cdn.yandex.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ArtistsService.class)
                .listArtists();

        list.enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                artists.clear();
                artists.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Artist>> call, Throwable t) {
                Snackbar.make(recyclerView, "OSHIBKA!", Snackbar.LENGTH_INDEFINITE).setAction("Повторить", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadArtists();
                    }
                }).show();
            }
        });

    }
}
