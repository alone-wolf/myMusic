package com.wh.mymusic.adapter

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
import com.wh.mymusic.util.ViewUtils
import kotlinx.android.synthetic.main.item_song_linear.view.*
import kotlinx.coroutines.*


class LocalSongListKotlinAdapter(activity: AppCompatActivity) :
        ListAdapter<Song, LocalSongListKotlinAdapter.SongViewHolder>(ThisDiffUtil()) {

    private val TAG = "WH_" + javaClass.simpleName
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

    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItemSongTitle: TextView = itemView.tv_item_song_title
        val tvItemSongArtist: TextView = itemView.tv_item_song_artist
        val ivItemSongCover: ImageView = itemView.iv_item_song_cover
        var p:Int = 0
        lateinit var song: Song
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song_linear, parent, false)
        val holder = SongViewHolder(view)
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

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setTag(R.id.local_song_tag, item)

        holder.tvItemSongTitle.text = item.title
        holder.tvItemSongArtist.text = item.artistName
        holder.song = item
        holder.p = position

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.argb(0.5f, 100f, 100f, 100f));
        } else {
            holder.itemView.setBackgroundColor(Color.argb(0f, 100f, 100f, 100f));
        }

        ViewUtils.setViewCornerRadius(holder.itemView)

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
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.ivItemSongCover)
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
        return@withContext MusicUtil.getMediaStoreAlbumCoverUri(song.albumId)
    }
}