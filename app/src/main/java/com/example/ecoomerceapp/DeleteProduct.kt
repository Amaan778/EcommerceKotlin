package com.example.ecoomerceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_manage_product.*

class DeleteProduct : AppCompatActivity() {
    val arrContact= ArrayList<Constructor>()
    private var db= Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_product)


        recycler.layoutManager= LinearLayoutManager(this)
        val adap=DeleteAdapter(this,arrContact)
        recycler.adapter=adap


        db= FirebaseFirestore.getInstance()
        db.collection("products").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                if (error!=null){
                    Log.e("firestore", error.message.toString())
                }

                for (dc : DocumentChange in value?.documentChanges!!){

                    if(dc.type == DocumentChange.Type.ADDED){
                        arrContact.add(dc.document.toObject(Constructor::class.java))
                    }

                }

                adap.notifyDataSetChanged()

            }
        })


    }
}