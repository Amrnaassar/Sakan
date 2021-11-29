package com.example.sakan

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rooms.*
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_chat.*
import android.provider.MediaStore as MediaStore1

class MainPage : AppCompatActivity() {
    private var comingImage: Bitmap? = null
    private var imageUri: Uri? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rooms)
        val database = Firebase.database
        val myRef = database.getReference("Posts")
        val posts = ArrayList<Post>()
        recelerview.layoutManager = LinearLayoutManager(this)

        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d("Find", "onChildAdded:" + dataSnapshot.key!!)

                // A new comment has been added, add it to the displayed list
                val ps =  dataSnapshot.getValue(Post::class.java)

                Log.d("TAG","1"+ps!!.desc.toString())

                Log.d("Find","2")
                posts.add(ps )
                Log.d("Find","3")
                adapter = RecyclerAdapter(posts)
                recelerview.adapter = adapter


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


        adapter = RecyclerAdapter(posts)
        recelerview.adapter = adapter


    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val myInflater = menuInflater
        myInflater.inflate(R.menu.main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.add_post -> {

                val intent= Intent(this,AddPostActivity::class.java)

                startActivity(intent)

                Toast.makeText(this, "hiii", Toast.LENGTH_LONG).show()
                return true

            }
            R.id.exit  -> {
                finish()
                return true
            }

        }

        return super.onOptionsItemSelected(item)
    }




}