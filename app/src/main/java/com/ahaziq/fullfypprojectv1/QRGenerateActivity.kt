package com.ahaziq.fullfypprojectv1

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_q_r_generate.*
import java.io.File
import java.io.FileOutputStream


class QRGenerateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q_r_generate)




        btn_generate.setOnClickListener {

            var stringQR = findViewById<EditText>(R.id.et_to_generate)
            var stringID = stringQR.text.toString()

            if (stringID.isEmpty()) {
                Toast.makeText(this, "Please enter ID", Toast.LENGTH_SHORT).show()


            } else {

                Toast.makeText(this, stringID, Toast.LENGTH_SHORT).show()


                val barcodeEncoder = BarcodeEncoder()
                val bitmap =
                    barcodeEncoder.encodeBitmap(stringID, BarcodeFormat.QR_CODE, 1000, 1000)

                imageView2.setImageBitmap(bitmap)

            }


        }


    }
}