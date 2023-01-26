package com.vlados_app.myapplication2.favourite.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vlados_app.myapplication2.R
import com.vlados_app.myapplication2.favourite.domain.FavouriteCatModel

class FavouriteAdapter(
    private val onDownloadClicked: (url: String) -> Unit
) : ListAdapter<FavouriteCatModel, FavouriteAdapter.ViewHolder>(FavouriteCatsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favourite_cat_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView
        private val downloadButton: Button
        lateinit var item: FavouriteCatModel

        init {
            imageView = view.findViewById(R.id.image_view)
            downloadButton = view.findViewById(R.id.download_button)

            downloadButton.setOnClickListener {
                onDownloadClicked(item.url)
            }
        }

        fun bind(item: FavouriteCatModel) {
            this.item = item
            Glide.with(imageView)
                .load(item.url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_wallpaper_default)
                .into(imageView)
        }
    }

    class FavouriteCatsDiffUtil : DiffUtil.ItemCallback<FavouriteCatModel>() {
        override fun areItemsTheSame(oldItem: FavouriteCatModel, newItem: FavouriteCatModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavouriteCatModel, newItem: FavouriteCatModel): Boolean {
            return oldItem == newItem
        }
    }
}