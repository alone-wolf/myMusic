package com.wh.mymusic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wh.mymusic.R
import com.wh.mymusic.viewmodel.WebDavPathListViewModel
import kotlinx.android.synthetic.main.item_web_dav_path.view.*

class WebDavPathListAdapter(val webDavPathListViewModel: WebDavPathListViewModel,var url:String) : ListAdapter<String, WebDavPathListAdapter.WebDavPathViewHolder>(ThisDiffUtil()) {

    class ThisDiffUtil : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    class WebDavPathViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvItemWebDavPathName:TextView = itemView.tv_item_web_dav_path_name
        lateinit var str:String
    }

    private val TAG = "WH_" + javaClass.simpleName
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebDavPathListAdapter.WebDavPathViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_web_dav_path, parent, false)
        val holder = WebDavPathViewHolder(view)
        view.setOnClickListener {
            url+=holder.str
            webDavPathListViewModel.setCurrentFullPath(url)
        }
        return holder
    }

    override fun onBindViewHolder(holder: WebDavPathListAdapter.WebDavPathViewHolder, position: Int) {
        val item:String = getItem(position)
        holder.itemView.setTag(R.id.web_dav_path,item)
        holder.tvItemWebDavPathName.text = item
        holder.str=item
    }


}