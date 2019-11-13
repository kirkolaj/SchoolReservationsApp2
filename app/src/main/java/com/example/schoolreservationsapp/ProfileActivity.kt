package com.example.schoolreservationsapp

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileActivity : AppCompatActivity() {

    internal lateinit var userEmail: TextView
    internal lateinit var userLogout: Button
    internal lateinit var roomRerservation: Button
    internal lateinit var deleteRoomRerservation: Button

    internal lateinit var firebaseAuth: FirebaseAuth

    internal lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userEmail = findViewById(R.id.tvUserEmail)
        userLogout = findViewById(R.id.btnLogout)
        roomRerservation = findViewById(R.id.btnMakeRerservation)
        deleteRoomRerservation = findViewById(R.id.btnDeleteReservation)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser!!

        userEmail.text = firebaseUser.email



        userLogout.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(this@ProfileActivity, MainActivity::class.java)

            startActivity(intent)
        }
        roomRerservation.setOnClickListener {
            val intent = Intent(this@ProfileActivity, MakeRersevationActivity::class.java)

            startActivity(intent)
        }

        deleteRoomRerservation.setOnClickListener {
            val intent = Intent(this@ProfileActivity, DeleteReservationActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.profile_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.reset -> {
                finish()
                return true
            }
            R.id.exit -> {
                firebaseAuth.signOut()
                val intent = Intent(this@ProfileActivity, MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
