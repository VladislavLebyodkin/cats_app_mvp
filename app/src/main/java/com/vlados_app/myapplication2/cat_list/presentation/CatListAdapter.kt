package com.vlados_app.myapplication2.cat_list.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vlados_app.myapplication2.R
import com.vlados_app.myapplication2.cat_list.data.Cat

class CatListAdapter(
    private val addToFavourite: (id: String) -> Unit,
) : ListAdapter<Cat, CatListAdapter.CatViewHolder>(CatsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cat_item, parent, false)

        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView
        private val breedTextView: TextView

        init {
            imageView = view.findViewById(R.id.image_view)
            breedTextView = view.findViewById(R.id.breed_text_view)
        }

        fun bind(item: Cat) {
            Glide.with(imageView)
                .load(item.url)
                .placeholder(R.drawable.ic_wallpaper_default)
                .into(imageView)

            breedTextView.text = item.id

            imageView.setOnClickListener {
                addToFavourite(item.id)
            }
        }
    }

    class CatsDiffUtil : DiffUtil.ItemCallback<Cat>() {
        override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem == newItem
        }
    }
}

