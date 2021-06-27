package com.ahaziq.fullfypprojectv1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_scan.*


const val TAG: String = "TAG"

class ScanActivity : AppCompatActivity() {


    companion object {
        val MATRIC_NUMBER_CURRENT: String = "Current Matric Number"
        var database = FirebaseDatabase.getInstance().reference
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        btnScanButton.setOnClickListener {
            scanQRCode()
        }
    }

    private fun scanQRCode() {
        val integrator = IntentIntegrator(this).apply {
            captureActivity = CaptureActivity::class.java
            setOrientationLocked(false)
            setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
            setPrompt("Scanning Code")
        }
        integrator.initiateScan()
    }

    // Get the results:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        // var database = FirebaseDatabase.getInstance().reference
        var matric_no = result.contents.toString()

        database.child(matric_no).setValue(User(matric_no))
        //pass the qr code value to the next activity (Control Activity)
        val intent = Intent(this, ControlActivity::class.java)
        intent.putExtra(MATRIC_NUMBER_CURRENT, matric_no)
        startActivity(intent)


        var updateSuccess = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                Toast.makeText(applicationContext, "Data saved to firebase", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Data save error", Toast.LENGTH_SHORT).show()
            }
        }


        if (result != null) {
            if (result.contents == null) Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            else Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

        database.addValueEventListener(updateSuccess)
        database.addListenerForSingleValueEvent(updateSuccess)
    }


}