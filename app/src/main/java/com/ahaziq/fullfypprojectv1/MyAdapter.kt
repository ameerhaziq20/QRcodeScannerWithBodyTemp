package com.ahaziq.fullfypprojectv1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val userList : ArrayList<User>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    //called when the recycler view needs a view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //itemView inflater from user_item layout
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item,
            parent,false)
        return MyViewHolder(itemView)

    }
    //for displaying data at the specified position
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //getting dad
        val currentitem = userList[position]
        //data from User data class
        holder.firstName.text = currentitem.firstName
        holder.lastName.text = currentitem.lastName
        holder.age.text = currentitem.age

    }
    //return total number of data held by adapter
    override fun getItemCount(): Int {
        return userList.size
    }

    //view holder class. necessary for itemView and its metadata in the RecyclerView.
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        //creates view, a base class for widgets.
        val firstName : TextView = itemView.findViewById(R.id.tvfirstName)
        val lastName : TextView = itemView.findViewById(R.id.tvlastName)
        val age : TextView = itemView.findViewById(R.id.tvage)

    }

}