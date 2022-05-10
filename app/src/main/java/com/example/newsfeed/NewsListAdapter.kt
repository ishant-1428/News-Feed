package com.example.newsfeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(private val listener : NewsItemClicked)
    : RecyclerView.Adapter<NewsHolder>() {

    private val items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_news,parent,false)
        val viewHolder = NewsHolder(view)
        view.setOnClickListener{
            listener.onItemClick(items[viewHolder.absoluteAdapterPosition])

        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.imageview)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updatedItems : ArrayList<News>){
        items.clear()
        items.addAll(updatedItems)

        notifyDataSetChanged()

    }


}


class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView:TextView = itemView.findViewById(R.id.title)
    val imageview: ImageView = itemView.findViewById(R.id.imageview)
    val author: TextView = itemView.findViewById(R.id.author)
}


interface NewsItemClicked{
    fun onItemClick(items: News){

    }
}