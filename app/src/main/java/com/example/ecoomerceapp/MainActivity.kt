package com.example.ecoomerceapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addImg.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,AddProduct::class.java))
            finish()
        })

        delete.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,DeleteProduct::class.java))
        })

        manage.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,ManageProduct::class.java))
        })




    }
}