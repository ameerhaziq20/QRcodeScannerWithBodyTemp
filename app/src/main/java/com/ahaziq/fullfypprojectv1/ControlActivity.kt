package com.ahaziq.fullfypprojectv1

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.client.android.Intents
import kotlinx.android.synthetic.main.control_layout.*
import java.io.*
import java.nio.charset.Charset
import java.util.*

class ControlActivity : AppCompatActivity() {



    companion object {
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var m_progress: ProgressDialog
        lateinit var m_bluetoothAdapter: BluetoothAdapter
        var m_isConnected: Boolean = false
        lateinit var m_address: String
        val sb = StringBuilder()
        lateinit var m_matricNumber:String


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.control_layout)
        m_address = intent.getStringExtra(SelectDeviceActivity.EXTRA_ADDRESS).toString()
        m_matricNumber = intent.getStringExtra(ScanActivity.MATRIC_NUMBER_CURRENT).toString()
        ConnectToDevice(this).execute()


        if (m_bluetoothSocket != null) {
            sendCommand()
            receiveData()
        }



    }

    private fun sendCommand() {
        //triggers the esp32
        val input = "a"
        if (m_bluetoothSocket != null) {
            try {
                m_bluetoothSocket!!.outputStream.write(input.toByteArray())


            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun receiveData() {


        var readMessage : String = ""

        m_bluetoothSocket!!.inputStream
        val buffer = ByteArray(1024)
        var bytes: Int
        //Loop to listen for received bluetooth messages
        while (!(readMessage.contains('\n'))){

            try {
                //read bytes received and ins to buffer
                bytes = m_bluetoothSocket!!.inputStream.read(buffer)
                //convert to string
                readMessage = readMessage + String(buffer, 0,bytes)
                var body_temp = readMessage.trim()

                ScanActivity.database.child(m_matricNumber).setValue(User(m_matricNumber,body_temp))


                sb.append(body_temp)
                tv_grab.text=sb

            } catch (e: IOException) {
                e.printStackTrace()
                break
            }
        }
    }


    private fun disconnect() {
        if (m_bluetoothSocket != null) {
            try {
                m_bluetoothSocket!!.close()
                m_bluetoothSocket = null
                m_isConnected = false
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        finish()
    }

    private class ConnectToDevice(c: Context) : AsyncTask<Void, Void, String>() {
        private var connectSuccess: Boolean = true
        private val context: Context

        init {
            this.context = c
        }

        override fun onPreExecute() {
            super.onPreExecute()
            m_progress = ProgressDialog.show(context, "Connecting...", "please wait")
        }

        override fun doInBackground(vararg p0: Void?): String? {
            try {
                if (m_bluetoothSocket == null || !m_isConnected) {
                    m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device: BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(m_address)
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    m_bluetoothSocket!!.connect()
                }
            } catch (e: IOException) {
                connectSuccess = false
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (!connectSuccess) {
                Log.i("data", "couldn't connect")
            } else {
                m_isConnected = true
            }
            m_progress.dismiss()
        }
    }
}