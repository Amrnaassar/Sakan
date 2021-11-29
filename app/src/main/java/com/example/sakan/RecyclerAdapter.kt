package com.example.sakan

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerAdapter(val posts: ArrayList<Post>) :RecyclerView.Adapter<RecyclerAdapter.ViewHolder >(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.post,parent,false)

        return  ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.title.text=posts[position].desc
        //holder.image.setImageBitmap(posts[position].image)
        Picasso.get().load(posts[position].image).into(holder.image)
        holder.btnLocation.setOnClickListener {
            Log.d("Find",posts[position].lat.toString())

        }

    }

    override fun getItemCount(): Int {
        return posts.size;
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById(R.id.image_item) as ImageView
        var title = itemView.findViewById(R.id.word) as TextView
        var btnLocation = itemView.findViewById(R.id.btnLocation) as TextView




    }

}