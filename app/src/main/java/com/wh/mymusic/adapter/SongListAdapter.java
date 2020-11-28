package com.wh.mymusic.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.wh.mymusic.BaseApp;
import com.wh.mymusic.PlayService;
import com.wh.mymusic.R;
import com.wh.mymusic.beam.Song;
import com.wh.mymusic.util.MusicUtil;

import java.io.IOException;


public class SongListAdapter extends ListAdapter<Song, SongListAdapter.AudioViewHolder> {
    private String TAG = "WH_" + getClass().getSimpleName();
    private AppCompatActivity activity;

    public SongListAdapter(AppCompatActivity activity) {
        super(new DiffUtil.ItemCallback<Song>() {
            @Override
            public boolean areItemsTheSame(@NonNull Song oldItem, @NonNull Song newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Song oldItem, @NonNull Song newItem) {
                return oldItem.getTitle().equals(newItem.getTitle()) &&
                        oldItem.getArtistName().equals(newItem.getArtistName());
            }
        });
        this.activity = activity;
    }

    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_audio, parent, false);
        AudioViewHolder audioViewHolder = new AudioViewHolder(itemView);
        audioViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), PlayService.class);
                intent.putExtra("id", audioViewHolder.position);
                intent.putExtra("title", audioViewHolder.song.getTitle());
                v.getContext().startService(intent);
            }
        });
        audioViewHolder.itemView.setOnLongClickListener(v -> {

            new AlertDialog.Builder(parent.getContext())
                    .setTitle("detail")
                    .setItems(new String[]{
                            "id " + audioViewHolder.song.getId(),
                            "title " + audioViewHolder.song.getTitle(),
                            "trackNumber " + audioViewHolder.song.getTrackNumber(),
                            "year " + audioViewHolder.song.getYear(),
                            "dateAdded " + audioViewHolder.song.getDateAdded(),
                            "dateModified " + audioViewHolder.song.getDateModified(),
                            "albumId " + audioViewHolder.song.getAlbumId(),
                            "albumName " + audioViewHolder.song.getAlbumName(),
                            "artistId " + audioViewHolder.song.getAlbumId(),
                            "artistName " + audioViewHolder.song.getAlbumName(),
                            "displayName " + audioViewHolder.song.getDisplayName(),
                            "relativePath " + audioViewHolder.song.getRelativePath(),
                            "data " + audioViewHolder.song.getData(),
                            "duration " + audioViewHolder.song.getDuration(),
                            "size " + audioViewHolder.song.getSize()}, null)
                    .create().show();

            return true;
        });
        return audioViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, int position) {
        Song song = getItem(position);
        holder.itemView.setTag(R.id.audio_tag, song);
        holder.tv_item_audio_title.setText(song.getTitle());
        holder.tv_item_audio_artist.setText(song.getArtistName());
        holder.song = song;
        holder.position = position;
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.argb(0.5f, 100f, 100f, 100f));
        } else {
            holder.itemView.setBackgroundColor(Color.argb(0f, 100f, 100f, 100f));
        }

        try {
            loadAlbumCover(song, holder);
        } catch (IOException e) {
            e.printStackTrace();
            holder.iv_item_audio_cover.setImageResource(R.drawable.ic_launcher_foreground);
        }
    }

    protected void loadAlbumCover(Song song, final AudioViewHolder holder) throws IOException {
        Log.d(TAG, "loadAlbumCover: ");
        if (holder.iv_item_audio_cover == null) {
            Log.d(TAG, "loadAlbumCover: null");
            return;
        }


        boolean has = false;
        Bitmap bitmap = null;
        for(Pair<Long,Bitmap> p: BaseApp.albumCovers){
            if(p.first==null){
                BaseApp.albumCovers.remove(p);
                continue;
            }
            if(song.getAlbumId() == p.first){
                has = true;
                bitmap = p.second;
                break;
            }
        }

        if(!has){
            Uri u = MusicUtil.getMediaStoreAlbumCoverUri(song.getAlbumId());
            bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), u);
            BaseApp.albumCovers.add(new Pair<>(song.getAlbumId(),bitmap));
        }
        holder.iv_item_audio_cover.setImageBitmap(bitmap);
    }

    public static class AudioViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_audio_title;
        TextView tv_item_audio_artist;
        ImageView iv_item_audio_cover;
        Song song;
        int position;

        public AudioViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item_audio_title = itemView.findViewById(R.id.tv_item_audio_title);
            tv_item_audio_artist = itemView.findViewById(R.id.tv_item_audio_artist);
            iv_item_audio_cover = itemView.findViewById(R.id.iv_item_audio_cover);
        }
    }
}
