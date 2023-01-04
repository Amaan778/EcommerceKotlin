package com.example.ecoomerceapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_slider_image.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.log

class SliderImage3 : AppCompatActivity() {

    private val PICK_IMAGES_CODE=0
    private var imageUri: Uri? = null

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider_image3)

        val getingint=intent.getStringExtra("productss")
        name.text=getingint

        val idgeting=intent.getStringExtra("idss")
        val id=idgeting.toString()
        Log.d("check", "onCreate: "+id)

        btnselect.setOnClickListener(View.OnClickListener {
            pickImages()
        })

        btnupload.setOnClickListener(View.OnClickListener {

            database= FirebaseDatabase.getInstance().getReference("slider")

//            val title=name.text.toString().trim()

            val db = Firebase.firestore

            val formatter= SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
            val now= Date()
            val filename=formatter.format(now)

            val storage = FirebaseStorage.getInstance().getReference("Sliders/$filename")
            imageUri?.let { it1 ->
                storage.putFile(it1).addOnCompleteListener {
                    if (it.isSuccessful) {
                        storage.downloadUrl.addOnSuccessListener { uri ->
                            val usermap = HashMap<String, Any>()

                            val update= mapOf(
                                "img3" to uri.toString()
                            )

                            db.collection("products").document(id).update(update)
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                            Toast.makeText(this,"image added", Toast.LENGTH_LONG).show()


                        }
                    }
                }
            }

        })

    }

    private  fun pickImages(){

        val intent= Intent()
        intent.type="image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
        intent.action= Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Images"),PICK_IMAGES_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGES_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                imageUri = data?.data
                img.setImageURI(imageUri)
            }
        }
    }
}