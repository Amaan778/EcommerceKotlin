package com.example.ecoomerceapp

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.get
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_add_product.view.*
import kotlinx.android.synthetic.main.activity_manage_product.*
import kotlinx.android.synthetic.main.addproductimg.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class AddProduct : AppCompatActivity() {

    private val PICK_IMAGES_CODE=0
    private var imageUri: Uri? = null

    val categories= arrayOf("Electronics", "Laptop", "Mobile")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        //category section
        val arrayAdapter=ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,categories)
        spin.adapter=arrayAdapter

        spin.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                text.text=categories[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //uploading products

        upload.setOnClickListener(View.OnClickListener {

            //progress bar

            val dialog=Dialog(this)
            dialog.setContentView(R.layout.dialogloading)
            dialog.setCancelable(false)
            dialog.show()


            //uploading details in firestore
            val title=product_Name.text.toString().trim()
            val description=product_Description.text.toString().trim()
            val selling=product_Price.text.toString().trim()
            val price=productselling.text.toString().trim()
            val texts=text.text.toString()

            if (title.isEmpty()){
                product_Name.error="Required"
            }else if (description.isEmpty()){
                product_Description.error="Required"
            }else if (selling.isEmpty()){
                product_Price.error="Required"
            }else if (price.isEmpty()){
                productselling.error="Required"
            }else{

                val db = Firebase.firestore

                val formatter= SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
                val now=Date()
                val filename=formatter.format(now)

                val storage = FirebaseStorage.getInstance().getReference("images/$filename")
                imageUri?.let { it1 ->
                    storage.putFile(it1).addOnCompleteListener {
                        if (it.isSuccessful) {
                            storage.downloadUrl.addOnSuccessListener { uri ->
                                val usermap = HashMap<String, Any>()
                                usermap["pic"] = uri.toString()
                                usermap["ProductName"]=title
                                usermap["description"]=description
                                usermap["sellingprice"]=selling
                                usermap["price"]=price
                                usermap["category"]=texts
                                usermap["img1"]=""
                                usermap["img2"]=""
                                usermap["img3"]=""

                                val id=UUID.randomUUID().toString()
                                usermap["id"]=id

                                db.collection("products").document(id).set(usermap)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            Toast.makeText(this, "Uploaded successful", Toast.LENGTH_LONG).show()
                                            dialog.dismiss()
                                            val intent=Intent(this,SliderImage::class.java)
                                            intent.putExtra("product_name",title)
                                            intent.putExtra("id",id)
                                            startActivity(intent)
                                            finish()

                                            product_Name.text.clear()
                                            product_Description.text.clear()
                                            product_Price.text.clear()
                                            productselling.text.clear()
                                            coverimg.setImageURI(null)


                                        } else {
                                            Toast.makeText(this, "Uploaded unsuccessful", Toast.LENGTH_LONG).show()
                                        }
                                    }
                            }
                        }
                    }
                }
            }
        })


        //cover image
        coverbtn.setOnClickListener(View.OnClickListener {
            pickImages()
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
                coverimg.setImageURI(imageUri)
            }
        }
    }


}