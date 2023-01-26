package com.vlados_app.myapplication2.cat_list.presentation

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
import com.vlados_app.myapplication2.cat_list.domain.CatModel

class CatListAdapter(
    private val onAddToFavouriteClicked: (id: String) -> Unit,
    private val onDownloadClicked: (url: String) -> Unit,
) : ListAdapter<CatModel, CatListAdapter.ViewHolder>(CatsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cat_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView
        private val addToFavouritesButton: Button
        private val downloadButton: Button
        lateinit var item: CatModel

        init {
            imageView = view.findViewById(R.id.image_view)
            addToFavouritesButton = view.findViewById(R.id.add_to_favourites_button)
            downloadButton = view.findViewById(R.id.download_button)

            addToFavouritesButton.setOnClickListener {
                onAddToFavouriteClicked(item.id)
            }

            downloadButton.setOnClickListener {
                onDownloadClicked(item.url)
            }
        }

        fun bind(item: CatModel) {
            this.item = item
            Glide.with(imageView)
                .load(item.url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_wallpaper_default)
                .into(imageView)

            addToFavouritesButton.visibility = if (item.isFavourite) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }

    class CatsDiffUtil : DiffUtil.ItemCallback<CatModel>() {
        override fun areItemsTheSame(oldItem: CatModel, newItem: CatModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CatModel, newItem: CatModel): Boolean {
            return oldItem == newItem
        }
    }
}

