package com.example.sakan

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sakan.ChatActivity.message

class MessageAdaper(val messages: ArrayList<message>) :RecyclerView.Adapter<MessageAdaper.ViewHolder >(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdaper.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.item_message,parent,false)

        return  ViewHolder(v)
    }

    override fun onBindViewHolder(holder: MessageAdaper.ViewHolder, position: Int) {
        holder.name.text=messages[position].name
        holder.image.setImageBitmap(null)
        holder.message.text=messages[position].messageText

    }

    override fun getItemCount(): Int {
        return messages.size;
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById(R.id.imageSender) as ImageView
        var message = itemView.findViewById(R.id.messageTextView) as TextView
        var name = itemView.findViewById(R.id.nameTextView) as TextView




    }

}