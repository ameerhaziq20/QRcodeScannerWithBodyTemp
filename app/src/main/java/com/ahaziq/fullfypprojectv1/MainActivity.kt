package com.ahaziq.fullfypprojectv1

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        val scanAct = findViewById<ImageButton>(R.id.ibtnScanAct)
        scanAct.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            startActivity(intent)
        }

        val listAct = findViewById<ImageButton>(R.id.ibtnListAct)
        listAct.setOnClickListener {
            val intent = Intent(this, UserlistActivity::class.java)
            startActivity(intent)
        }

        val btAct = findViewById<ImageButton>(R.id.ibtnbtAct)
        btAct.setOnClickListener {
            val intent = Intent(this, SelectDeviceActivity::class.java)
            startActivity(intent)
        }


    }
}