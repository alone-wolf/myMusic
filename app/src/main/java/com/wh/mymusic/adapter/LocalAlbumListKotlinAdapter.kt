package com.wh.mymusic.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wh.mymusic.R
import com.wh.mymusic.beam.AudioAlbum
import com.wh.mymusic.beam.Song
import com.wh.mymusic.util.MusicUtil
import com.wh.mymusic.util.ViewUtils
import kotlinx.android.synthetic.main.item_album_pager.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocalAlbumListKotlinAdapter(activity: AppCompatActivity)
    : ListAdapter<AudioAlbum,LocalAlbumListKotlinAdapter.AlbumViewHolder>(ThisDiffUtil()){
    private val TAG = "WH_" + javaClass.simpleName
    val a = activity


    class ThisDiffUtil:DiffUtil.ItemCallback<AudioAlbum>(){
        override fun areItemsTheSame(oldItem: AudioAlbum, newItem: AudioAlbum): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AudioAlbum, newItem: AudioAlbum): Boolean {
            return newItem.albumId == newItem.albumId
        }

    }

    class AlbumViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val ivItemAlbumCover:ImageView = itemView.iv_item_album_cover
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album_pager, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setTag(R.id.local_album_tag,item)
        ViewUtils.setViewCornerRadius(holder.ivItemAlbumCover)

        GlobalScope.launch(Dispatchers.Main) {
            val uri = loadAlbumCoverUri(item)
            Glide.with(holder.itemView)
                    .load(uri)
                    .centerCrop()
                    .placeholder(R.drawable.below_shadow)
                    .into(holder.ivItemAlbumCover)
        }
    }

    private suspend fun loadAlbumCoverUri(album: AudioAlbum) = withContext(Dispatchers.IO){
        Log.d(TAG, "loadAlbumCover: ")

        return@withContext MusicUtil.getMediaStoreAlbumCoverUri(album.albumId)
    }

}