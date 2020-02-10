package fr.isen.facedog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_general_feed.*

class GeneralFeedActivity : AppCompatActivity() {
    lateinit var toolbar: ActionBar
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_feed)
        auth = FirebaseAuth.getInstance()
        toolbar = supportActionBar!!
        buttonswitch.setOnClickListener{
            auth.signOut()
            auth.addAuthStateListener {
                intent = Intent(this, ConnectionActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }



}

