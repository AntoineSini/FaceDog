package fr.isen.facedog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import fr.isen.facedog.classes.LatestPublication
import fr.isen.facedog.classes.Publication
import kotlinx.android.synthetic.main.activity_general_feed.*

class GeneralFeedActivity : AppCompatActivity(), RecyclerAdapterFeed.OnPublicationRecycleListener{
    lateinit var toolbar: ActionBar
    lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_feed)
        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        recyclerAndDatabaseHandler()
        
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
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
    fun recyclerAndDatabaseHandler(){
        RecyclerFeed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //database request pour récuperer un objet de type LatestPublication

        // ---------------- EXAMPLE --------------------

        val publi = Publication(
            id = "123", description = "La description " +
                    "de cette publication est particulièrement plus longue que le titre tout de même",
            title = "Bonjour ! Ceci est une publication !", user_id = "XT4UAstT94flVAdARB8HE1wpPbz1"
        )
        val publi2 = Publication(
            id = "1234", description = "La description " +
                    "de cette publication est particulièrement plus longue que le titre tout de même",
            title = "Lol on se fend la gueule", user_id = "XT4UAstT94flVAdARB8HE1wpPbz1"
        )
        val tabPublis = LatestPublication()
        tabPublis.results.add(publi)
        tabPublis.results.add(publi2)

        // ---------------- EXAMPLE --------------------

        val adapter = RecyclerAdapterFeed(content = tabPublis, listener = this)
        RecyclerFeed.adapter = adapter

    }

    override fun onSelectPublication(user: Publication?) {
        /*val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)*/
    }
}

