package com.example.sakan

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toRegister.setOnClickListener{
            val intent= Intent(this,ChatActivity::class.java)

            startActivity(intent)
        }
        btnlogin.setOnClickListener{
            val intent= Intent(this,MainPage::class.java)

            startActivity(intent)

        }












    }


}