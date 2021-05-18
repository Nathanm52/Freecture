package com.area.freecture

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.area.freecture.listeners.ListItemClickListener
import com.area.freecture.model.ImageModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class PhotoListAdapter(
    private val context: Context,
    private val list: List<ImageModel>,
    private var listener: ListItemClickListener) : RecyclerView.Adapter<PhotoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoListAdapter.ViewHolder, position: Int) {
        Glide.with(context).load(list[position].small)
            .thumbnail(0.5f)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.image)

        holder.lytParent.setOnClickListener {
            listener.onListItemClick(holder.adapterPosition, "imageClick")
            println("ici la position : $position")
            val intent = Intent(context, Picture::class.java)
            val myList = ArrayList<ImageModel>(list)
            intent.putExtra("mylist", myList)
            intent.putExtra("postion", position)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var image: ImageView = itemView.findViewById(R.id.image)
        internal var lytParent: CardView = itemView.findViewById(R.id.card_view)
    }
}

