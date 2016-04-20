package com.pavel.yandexpavel;

import com.pavel.yandexpavel.model.Artist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Интерфейс для парсинга JSON
 */
public interface ArtistsService {

    @GET("mobilization-2016/artists.json")
    Call<List<Artist>> listArtists();

}
