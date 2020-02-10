package fr.isen.facedog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_personal_page.*
import com.google.firebase.database.DatabaseReference
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.isen.facedog.classes.Publication
import kotlinx.android.synthetic.main.activity_general_feed.*


class PersonalPageActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        var adapter: RecyclerAdapterFeed? = null
        val publicationList = ArrayList<Publication>()
        layoutManager = LinearLayoutManager(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_page)
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        //val key = database.child("users").push().key ?: ""
        database.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.getValue(User::class.java)
                var currentUser = auth.currentUser?.uid
                var email: String? = auth.currentUser?.email
                var username: String? = auth.currentUser?.displayName
                textViewUsername.text = "${email}"
                textViewDescription.text = "${username}"

            }

            override fun onCancelled(error: DatabaseError) {
                //print error.message
            }
        })

        signOutButton.setOnClickListener {
            auth.signOut()
            auth.addAuthStateListener {
                intent = Intent(this, ConnectionActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
        /*adapter = RecyclerAdapterFeed()
        recyclerViewPersonalPosts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //adapter = RecyclerAdapterFeed(content = publicationList, listener = this)
        recyclerViewPersonalPosts.adapter = adapter*/
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

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser == null){
            auth.signOut()
        }
    }
}
