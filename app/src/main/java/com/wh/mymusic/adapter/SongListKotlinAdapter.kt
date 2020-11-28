package com.wh.mymusic.adapter

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wh.mymusic.BaseApp
import com.wh.mymusic.PlayService
import com.wh.mymusic.R
import com.wh.mymusic.beam.Song
import com.wh.mymusic.util.MusicUtil
import kotlinx.android.synthetic.main.item_audio.view.*
import kotlinx.coroutines.*


class SongListKotlinAdapter(activity: AppCompatActivity) :
        ListAdapter<Song, SongListKotlinAdapter.AudioViewHolder>(ThisDiffUtil()) {

    val TAG = "WH_" + javaClass.simpleName
    val a = activity

    class ThisDiffUtil : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.title == newItem.title
                    && oldItem.trackNumber == newItem.trackNumber
                    && oldItem.year == newItem.year
                    && oldItem.dateAdded == newItem.dateAdded
                    && oldItem.dateModified == newItem.dateModified
                    && oldItem.albumId == newItem.albumId
                    && oldItem.albumName == newItem.albumName
                    && oldItem.artistId == newItem.artistId
                    && oldItem.artistName == newItem.artistName
                    && oldItem.displayName == newItem.displayName
                    && oldItem.relativePath == newItem.relativePath
                    && oldItem.data == newItem.data
                    && oldItem.duration == newItem.duration
                    && oldItem.size == newItem.size
        }

    }

    inner class AudioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_item_audio_title = itemView.tv_item_audio_title
        val tv_item_audio_artist = itemView.tv_item_audio_artist
        val iv_item_audio_cover = itemView.iv_item_audio_cover
        var p:Int = 0
        lateinit var song: Song
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_audio, parent, false)
        val holder = AudioViewHolder(view)
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context.applicationContext, PlayService::class.java)
            intent.putExtra("id", holder.p)
            intent.putExtra("title", holder.song.title)
            it.context.startService(intent)
        }
        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(parent.context)
                    .setTitle("detail")
                    .setItems(arrayOf(
                            "id " + holder.song.id,
                            "title " + holder.song.title,
                            "trackNumber " + holder.song.trackNumber,
                            "year " + holder.song.year,
                            "dateAdded " + holder.song.dateAdded,
                            "dateModified " + holder.song.dateModified,
                            "albumId " + holder.song.albumId,
                            "albumName " + holder.song.albumName,
                            "artistId " + holder.song.artistId,
                            "artistName " + holder.song.artistName,
                            "displayName " + holder.song.displayName,
                            "relativePath " + holder.song.relativePath,
                            "data " + holder.song.data,
                            "duration " + holder.song.duration,
                            "size " + holder.song.size), null)
                    .create().show()
            true
        }
        return holder
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setTag(R.id.audio_tag, item)

        holder.tv_item_audio_title.text = item.title
        holder.tv_item_audio_artist.text = item.artistName
        holder.song = item
        holder.p = position

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.argb(0.5f, 100f, 100f, 100f));
        } else {
            holder.itemView.setBackgroundColor(Color.argb(0f, 100f, 100f, 100f));

        }

//         GlobalScope.launch(Dispatchers.Main) {
//             val bm = loadAlbumCover(item, a)
//             if(bm!=null){
//             holder.iv_item_audio_cover.setImageBitmap(bm)
//             }
//         }

        GlobalScope.launch(Dispatchers.Main) {
            val uri = loadAlbumCoverUri(item,a)
            Glide.with(holder.itemView)
                    .load(uri)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.iv_item_audio_cover)
        }


    }

    private suspend fun loadAlbumCover(song: Song, activity: AppCompatActivity) = withContext(Dispatchers.IO){
        Log.d(TAG, "loadAlbumCover: ")
        var has = false
        var bitmap: Bitmap? = null
        for (p in BaseApp.albumCovers) {
            if (p.first == null) {
                BaseApp.albumCovers.remove(p)
                continue
            }
            if (song.albumId == p.first) {
                has = true
                bitmap = p.second
                break
            }
        }

        if (!has) {
            val u = MusicUtil.getMediaStoreAlbumCoverUri(song.albumId)
            bitmap = try {
                MediaStore.Images.Media.getBitmap(activity.contentResolver, u)
            }catch (e: Exception){
                null;
            }
            BaseApp.albumCovers.add(Pair(song.albumId, bitmap))
        }

        return@withContext bitmap
    }

    private suspend fun loadAlbumCoverUri(song: Song, activity: AppCompatActivity) = withContext(Dispatchers.IO){
        Log.d(TAG, "loadAlbumCover: ")

        return@withContext MusicUtil.getMediaStoreAlbumCoverUri(song.albumId)
    }
}