package com.pavel.yandexpavel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pavel.yandexpavel.model.Artist;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

/**
 * Created by Павел on 16.04.2016.
 */
public class ArtistRecyclerViewAdapter extends RecyclerView.Adapter<ArtistRecyclerViewAdapter.ViewHolder> {

    private final List<Artist> artists;

    public ArtistRecyclerViewAdapter(List<Artist> artists) {
        this.artists = artists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element_artist, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Привязывает каждую сущность из листа артистов к объекту в RecyclerView
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Artist artist = artists.get(position);

        Picasso.with(holder.image.getContext())
                .load(artist.getCover().getSmall())
                .fit()
                .into(holder.image);

        holder.name.setText(artist.getName());


        String genres = "";
        if (artist.getGenres().size() > 1) {
            for (String genre : artist.getGenres()) {
                genres += genre + ", ";
            }
            genres = genres.substring(0, genres.length() - 2);
        } else if (artist.getGenres().size() == 1) {
            genres += artist.getGenres().get(0);
        }
        holder.genres.setText(genres);

        holder.stuff.setText(String.format(Locale.US, "%d альбомов, %d песен", artist.getAlbums(), artist.getTracks()));
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return artists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView genres;
        TextView stuff;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.artist_image);
            name = (TextView) itemView.findViewById(R.id.artist_name);
            genres = (TextView) itemView.findViewById(R.id.artist_genres);
            stuff = (TextView) itemView.findViewById(R.id.artist_stuff);
        }
    }
}