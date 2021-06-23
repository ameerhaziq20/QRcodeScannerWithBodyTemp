package com.ahaziq.fullfypprojectv1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class UserlistActivity : AppCompatActivity() {
    //initialize property outside of a constructor
    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<User>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userlist)
        //resources for recycler view.
        userRecyclerview = findViewById(R.id.userList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        userArrayList = arrayListOf<User>()
        getUserData()

    }
    //fetch user data from the firebase realtime database
    private fun getUserData() {
        //give the database an offline capability.
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        //points to a location in the database, in this case, the "Users" child.
        dbref = FirebaseDatabase.getInstance().getReference("Users")
        //listener during event changes.
        dbref.addValueEventListener(object : ValueEventListener{
            //called when the data changes
            override fun onDataChange(snapshot: DataSnapshot) {
                //check if the data from database exist
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        //gets value from database and store it in User data class
                        val user = userSnapshot.getValue(User::class.java)
                        //add data to list array
                        userArrayList.add(user!!)

                    }
                    //attach the recycler view adapter.
                    userRecyclerview.adapter = MyAdapter(userArrayList)


                }

            }
            //if listener failed.
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"Data Error", Toast.LENGTH_SHORT).show()
            }


        })

    }
}