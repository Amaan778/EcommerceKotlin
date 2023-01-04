package com.example.ecoomerceapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.delete.view.*
import kotlinx.android.synthetic.main.list.view.*
import kotlinx.android.synthetic.main.list.view.productimg
import kotlinx.android.synthetic.main.list.view.productname
import kotlinx.android.synthetic.main.list.view.sellingprice

class DeleteAdapter(val context: Context, val arrlist:ArrayList<Constructor>):
    RecyclerView.Adapter<DeleteAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val img=itemView.productimg
        val product_name=itemView.productname
        val price=itemView.sellingprice
        val delete=itemView.delete
    }



    override fun getItemCount(): Int {
        return arrlist.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeleteAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.delete, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(arrlist[position].pic).into(holder.img)
        holder.product_name.text=arrlist[position].ProductName
        holder.price.text=arrlist[position].sellingprice
    }


}