package gdg.androidtitlan.spotifymvp.example.ui.adapter;

/**
 * Copyright 2015 Erik Jhordan Rey.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import gdg.androidtitlan.spotifymvp.R;
import gdg.androidtitlan.spotifymvp.example.api.model.Artist;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistsViewHolder> {

  private List<Artist> artists;
  private ItemClickListener itemClickListener;

  public ArtistsAdapter() {
    artists = Collections.emptyList();
  }

  @Override public ArtistsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View itemView =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist, parent, false);
    return new ArtistsViewHolder(itemView);
  }

  @Override public void onBindViewHolder(ArtistsViewHolder holder, int position) {
    Artist artist = artists.get(position);
    holder.artist = artist;
    holder.textView.setText(artist.name);

    if (artist.artistImages.size() > 0) {
      holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
      for (int i = 0; i < artist.artistImages.size(); i++) {
        if (artist.artistImages.get(i) != null && artist.artistImages.size() > 0) {
          Picasso.with(holder.imageView.getContext())
              .load(artist.artistImages.get(0).url)
              .into(holder.imageView);
        }
      }
    } else {
      final String imageHolder =
          "http://d2c87l0yth4zbw-2.global.ssl.fastly.net/i/_global/open-graph-default.png";
      Picasso.with(holder.imageView.getContext()).load(imageHolder).into(holder.imageView);
    }

    holder.itemView.setOnClickListener((View view) -> {
      if (itemClickListener != null) itemClickListener.onItemClick(artist, position);
    });
  }

  @Override public int getItemCount() {
    return artists.size();
  }

  public static class ArtistsViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.img_view_artist_image) ImageView imageView;
    @Bind(R.id.txt_artist_name) TextView textView;

    Artist artist;
    View itemView;

    public ArtistsViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      this.itemView = itemView;
    }
  }

  public void setArtists(List<Artist> artists) {
    this.artists = artists;
  }

  public void setItemClickListener(ItemClickListener itemClickListener) {
    this.itemClickListener = itemClickListener;
  }

  public interface ItemClickListener {
    void onItemClick(Artist artist, int position);
  }
}
