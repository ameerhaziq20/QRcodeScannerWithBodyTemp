package com.ahaziq.fullfypprojectv1

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class BluetoothPairedAdapter(
    private var titles: List<String>,
    private var details: List<String>,
    //   private var images: List<Int>
) :
    RecyclerView.Adapter<BluetoothPairedAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemTitle: TextView = itemView.findViewById(R.id.tv_title)
        val itemDetails: TextView = itemView.findViewById(R.id.tv_description)
   //     val itemImage: ImageView = itemView.findViewById(R.id.iv_image)

        init {
            itemView.setOnClickListener {


                val position: Int = adapterPosition
                val address: String = details[position]
                val name: String = titles[position]


                Toast.makeText(
                    itemView.context,
                    "Device name is $name and the Address is $address",
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(itemView.context, ControlActivity::class.java)
                intent.putExtra(SelectDevice.EXTRA_ADDRESS, address)
                Toast.makeText(
                    itemView.context,
                    "Connected to Temperature Scanner",
                    Toast.LENGTH_SHORT
                ).show()
                itemView.context.startActivity(intent)
            }


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.bluetooth_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemDetails.text = details[position]
     //   holder.itemImage.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}

