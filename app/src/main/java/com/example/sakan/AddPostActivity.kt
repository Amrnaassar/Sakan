package com.example.sakan

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_post.*
import kotlinx.android.synthetic.main.activity_chat.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddPostActivity : AppCompatActivity() {
    private var comingImage: Bitmap? = null
    private var imageUri: Uri? = null
    private var mprogress: ProgressDialog?=null
    var imgUri:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
        val database = Firebase.database
        val myRef = database.getReference("Posts")


        val posts = ArrayList<Post>()
        btnAddPost.setOnClickListener {

            var intent = intent
            Log.d("TAG", "1")
            var lt:Double?=null
            var lg:Double?=null
            if((intent.getStringExtra("latitude"))!=null) {
                 lt = (intent.getStringExtra("latitude")).toString() as Double
                 lg = (intent.getStringExtra("longitude")).toString() as Double
            }
            Log.d("TAG", "2")








            Log.d("TAG", imgUri.toString())
            Log.d("TAG", "4")
            Log.d("TAG", "5")



            myRef.push().setValue(Post(descinput.text.toString(), imgUri.toString(), lt,lg))
            comingImage = null
            image.setImageBitmap(null)
            descinput.setText(null)
            imgUri=null

            startActivity(Intent(this,MainPage::class.java))

        }

        btnclose.setOnClickListener {
          //  add_post_box.visibility= View.INVISIBLE
        }
        openCamera.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 12)

        }
        openGallary.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 123)
        }
        openLocation.setOnClickListener {
            val mapintent = Intent(this, MapsActivity::class.java)
            startActivity(mapintent)


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123 && resultCode == RESULT_OK) {
            var bmp = data?.data
            imageUri=bmp
            UploadImage(imageUri!!)


            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), bmp)
            comingImage = bitmap
            image.setImageBitmap(bitmap)

        }
        if (requestCode == 12 && resultCode == RESULT_OK) {
            var bmp = data?.extras?.get("data") as Bitmap
            comingImage = bmp
            val byt= ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, byt)
            val path: String = MediaStore.Images.Media.insertImage(
                this.getContentResolver(),
                bmp,
                "Title",
                null
            )


           /*
            image.setImageBitmap(bmp)
            var bytes:ByteArrayOutputStream= ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.JPEG,100,bytes)
            var p=MediaStore.Images.Media.insertImage(contentResolver,bmp,null,null)
            var uri :Uri=Uri.parse(p)
            image.setImageURI(uri)*/

            UploadImage( Uri.parse(path)!!)


        }
    }

    private fun UploadImage(uriImage: Uri) {
        progbar.visibility=View.VISIBLE
        val formater=SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now=Date()
        val filename=formater.format(now)+".jpg"
        Log.d("TAG", filename.toString())
        val storageRef=FirebaseStorage.getInstance().reference
        val path:StorageReference?= storageRef?.child("PostsImage").child(filename.toString())
        path?.putFile(uriImage!!)?.
        addOnSuccessListener {

            Log.d("TAG", path.downloadUrl.toString()+"ana")

            path.downloadUrl.addOnSuccessListener {
                imgUri=it.toString()
                Log.d("Find", imgUri.toString())

            }

        }?.addOnFailureListener{
            Log.d("TAG", "x")

        }
        progbar.visibility=View.INVISIBLE


    }


}