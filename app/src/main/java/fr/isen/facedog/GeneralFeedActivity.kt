package fr.isen.facedog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class GeneralFeedActivity : AppCompatActivity() {
    lateinit var toolbar: ActionBar
    lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_feed)

        //authentication part
        auth = FirebaseAuth.getInstance()

        //database part
        database = FirebaseDatabase.getInstance().reference

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav_bar)

        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)

    }

    private val navigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.add_item -> {
                intent= Intent(this, NewPostActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.perso_item -> {
                intent= Intent(this, PersonalPageActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.feed_item -> {
                intent= Intent(this, GeneralFeedActivity::class.java)
                startActivity(intent)
                true
            }
        }
        false
    }


    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

        if(currentUser == null)
        {
            auth.signOut()
            intent= Intent(this, ConnectionActivity::class.java)
            startActivity(intent)
        }
    }



}

