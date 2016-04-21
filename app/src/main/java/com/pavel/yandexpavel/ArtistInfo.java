package com.pavel.yandexpavel;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.pavel.yandexpavel.model.Artist;
import com.squareup.picasso.Picasso;

import java.util.Locale;


public class ArtistInfo extends AppCompatActivity {

    private Artist artist;

    public static final String TAG = "artist_tag";

    private ImageView imageView;
    private TextView textStules;
    private TextView textAlbums;
    private TextView textAboutAuthor;


    /**
     * Переопределяем метод для того что бы кпока в ActinBar
     * действовала так же как и кнопка отмены последнего действия.
     * Это нужно легкий способо избежать очередной загрузки данных
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Метод ызывается при переходе к этому Activity
     * Здесь наша задача разме установиться данные соотвествуюему элементу
     * и закэшировать большую фотографию
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_layut);

        //получем данные из intent
        artist = getIntent().getParcelableExtra(TAG);

        //устанавливаем имя в ActionBar
        setTitle(artist.getName());

        // Инициализируем элементы
        initElements();

        //собираем строку жанров
        String genres = "";
        if (artist.getGenres().size() > 1) {
            for (String genre : artist.getGenres()) {
                genres += genre + ", ";
            }
            genres = genres.substring(0, genres.length() - 2);
        } else if (artist.getGenres().size() == 1)
            genres += artist.getGenres().get(0);
        textStules.setText(genres);

        //устанавливаем иформацию об эльбомах и песнях
        textAlbums.setText(String.format(Locale.US, "%d альбомов, %d песен", artist.getAlbums(), artist.getTracks()));

        //Приводим первую букву Биграфии к ВЕРХНЕМУ регистру
        String tx = (artist.getDescription().charAt(0) + "").toUpperCase() + artist.getDescription().substring(1, artist.getDescription().length() - 1);
        textAboutAuthor.setText(tx);



        //прогружаем и кэшируем большую фотографию
        Picasso.with(imageView.getContext())
                .load(artist.getCover().getBig())
                .placeholder(R.drawable.user_placehlder)
                .fit()
                .centerCrop()
                .into(imageView);

    }

    /**
     * Иницилизируем элементы
     */
    private void initElements() {

        imageView = (ImageView) findViewById(R.id.BigPhoto);
        textStules = (TextView) findViewById(R.id.stules);
        textAlbums = (TextView) findViewById(R.id.albums);
        textAboutAuthor = (TextView) findViewById(R.id.AboutAuthor);

    }


}
