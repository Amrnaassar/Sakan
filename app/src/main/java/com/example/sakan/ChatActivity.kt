package com.example.sakan

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.item_message.view.*
import kotlinx.android.synthetic.main.rooms.*
import java.util.*
import kotlin.collections.ArrayList as ArrayList1

class ChatActivity : AppCompatActivity() {

    private val mainContext:Context = this
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<MessageAdaper.ViewHolder>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val database = Firebase.database
        val myRef = database.getReference("message")


        val Messages = ArrayList<message>()
        message_view.layoutManager = LinearLayoutManager(this)

        adapter = MessageAdaper(Messages)
        message_view.adapter = adapter





        btnsend.setOnClickListener{
            myRef.push().setValue(message("amr",textMessage.text.toString()))
            textMessage.setText("")

        }

        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d("Find", "onChildAdded:" + dataSnapshot.key!!)

                // A new comment has been added, add it to the displayed list
                val mesg =  dataSnapshot.getValue(message::class.java)

                Log.d("Find","1"+mesg!!.messageText.toString())

                Log.d("Find","2")
                Messages.add(mesg )
                Log.d("Find","3")
                adapter = MessageAdaper(Messages)
                message_view.adapter = adapter


                // ...
            Log.d("Find","4")
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {


                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
               // val newComment = dataSnapshot.getValue<Comment>()
                //val commentKey = dataSnapshot.key

                // ...
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                val commentKey = dataSnapshot.key

                // ...
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
               // val movedComment = dataSnapshot.getValue<Comment>()
                //val commentKey = dataSnapshot.key

                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
//                Toast.makeText(context, "Failed to load comments.",
                //Toast.LENGTH_SHORT).show()
            }
        }
        myRef.addChildEventListener(childEventListener)






    }
    /*
   class myAdapter(context:Context,messages: ArrayList1<message>):BaseAdapter(){
        val myContext:Context
        val myMessages: ArrayList1<message>

        init {
            this.myContext=context
            this.myMessages=messages
        }

        override fun getCount(): Int {
            return myMessages.size

        }

        override fun getItem(position: Int): Any {
            return ""
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myinflater=LayoutInflater.from(myContext).inflate(R.layout.item_message,parent,false)
            myinflater.nameTextView.text=myMessages[position].name
            myinflater.messageTextView.text=myMessages[position].messageText
            myinflater.imageSender.setImageResource(R.drawable.instagram)
            return myinflater


        }


    }
*/

        public class message{
            var name: String?=null
            var messageText:String?=null
            constructor(name: String?, messageText: String?) {
                this.name = name
                this.messageText = messageText
            }

            constructor() {
            }
        }

}