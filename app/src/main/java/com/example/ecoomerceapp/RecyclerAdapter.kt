package com.example.ecoomerceapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list.view.*

class RecyclerAdapter(val context: Context, val arrlist:ArrayList<Constructor>):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        val img=itemView.productimg
        val product_name=itemView.productname
        val price=itemView.sellingprice
        val category=itemView.category

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        Glide.with(context).load(arrlist[position].pic).into(holder.img)
        holder.product_name.text=arrlist[position].ProductName
        holder.price.text=arrlist[position].sellingprice
        holder.category.text=arrlist[position].category
    }

    override fun getItemCount(): Int {
        return arrlist.size
    }


}