package com.ahaziq.fullfypprojectv1

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_bluetooth_pairing.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.select_device_layout.*


class SelectDeviceRV : AppCompatActivity() {
    private var titlesList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imagesList = mutableListOf<Int>()

    //for bluetooth part
    private var m_bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var m_pairedDevices: Set<BluetoothDevice>
    private val REQUEST_ENABLE_BLUETOOTH = 1

    companion object {
        val EXTRA_ADDRESS: String = "Device_address"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_pairing)

        checkBluetoothSupport()
        postToList()



        rv_recyclerview.layoutManager = LinearLayoutManager(this)
        rv_recyclerview.adapter = BluetoothPairedAdapter(titlesList, descList)
    }

    private fun checkBluetoothSupport() {
        m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (m_bluetoothAdapter == null) {
            Toast.makeText(applicationContext, "Bluetooth Not Supported", Toast.LENGTH_SHORT).show()
            return
        }
        if (!m_bluetoothAdapter!!.isEnabled) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
        }
        btn_refresh.setOnClickListener {

            postToList()
            Toast.makeText(applicationContext, "Device List Updated", Toast.LENGTH_SHORT).show()

        }

    }

/*    private fun pairedDeviceList() {
        m_pairedDevices = m_bluetoothAdapter!!.bondedDevices
        val list : ArrayList<BluetoothDevice> = ArrayList()

        if (!m_pairedDevices.isEmpty()) {
            for (device: BluetoothDevice in m_pairedDevices) {
                list.add(device)
                Log.i("device", ""+device.name)
            }
        } else {
            Toast.makeText(applicationContext,"No Paired Devices Available",Toast.LENGTH_SHORT).show()
        }
    }*/

    private fun addToList(title: String, description: String) {
        titlesList.add(title)
        descList.add(description)

    }

    private fun postToList() {
        m_pairedDevices = m_bluetoothAdapter!!.bondedDevices



        for (device: BluetoothDevice in m_pairedDevices) {
            addToList(device.name, device.address)
            Log.i("device", "" + device.name)


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if (resultCode == Activity.RESULT_OK) {
                if (m_bluetoothAdapter!!.isEnabled) {
                    Toast.makeText(applicationContext, "Bluetooth Enabled", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(applicationContext, "Bluetooth Disabled", Toast.LENGTH_SHORT)
                        .show()
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(
                    applicationContext,
                    "Canceled Bluetooth Enabling ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}