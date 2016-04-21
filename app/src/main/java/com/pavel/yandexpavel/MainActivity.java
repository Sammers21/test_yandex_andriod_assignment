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

    private RecyclerView recyclerView;
    private ArtistRecyclerViewAdapter adapter;
    private List<Artist> artists;
    private Call<List<Artist>> list;

    /**
     * Метод вызывается при создании Акстиити
     * а так же при других сецнариях жизненного цикла
     * @param savedInstanceState
     */
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
            /**
             * Проиходит в случае если пользователь нажал на элемент
             * @param view
             * @param position нмоер эелмента на который кликнули
             */
            @Override
            public void onItemClick(View view, int position) {
                Intent questionIntent = new Intent(MainActivity.this, ArtistInfo.class);

                //Передаём данные о ортисте на
                questionIntent.putExtra(ArtistInfo.TAG, artists.get(position));
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        // the context of the activity
                        MainActivity.this,

                        /**
                         * устанавливаем анимацию перехода между Активити
                         */
                        new Pair<View, String>(view.findViewById(R.id.artist_image),
                                getString(R.string.transition_name_BigPhoto)),
                        new Pair<View, String>(view.findViewById(R.id.artist_genres),
                                getString(R.string.transition_name_geners)),
                        new Pair<View, String>(view.findViewById(R.id.artist_stuff),
                                getString(R.string.transition_name_albums))

                );
                ActivityCompat.startActivity(MainActivity.this, questionIntent, options.toBundle());

            }
        }));


        loadArtists();
    }

    /**
     * Метод для получения и парсинга данных
     */
    private void loadArtists() {
        list = new Retrofit.Builder()
                .baseUrl("http://download.cdn.yandex.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ArtistsService.class)
                .listArtists();

        list.enqueue(new Callback<List<Artist>>() {
            /**
             * Получем и парсим данные
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                artists.clear();
                artists.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            /**
             * В случае если не удалось получить данные из JSON
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<List<Artist>> call, Throwable t) {
                Snackbar.make(recyclerView, "Internet ERROR", Snackbar.LENGTH_INDEFINITE).setAction("Повторить", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadArtists();
                    }
                }).show();
            }
        });

    }
}
