package fr.isen.facedog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import fr.isen.facedog.classes.LatestPublication
import fr.isen.facedog.classes.Publication
import kotlinx.android.synthetic.main.activity_general_feed.*


class GeneralFeedActivity : AppCompatActivity(), RecyclerAdapterFeed.OnPublicationRecycleListener{
    lateinit var toolbar: ActionBar
    lateinit var database: DatabaseReference
    lateinit var storage: FirebaseStorage

    lateinit var auth: FirebaseAuth





    fun downloadPosts(){
        val posts = database.child("publication")

        posts.addValueEventListener(object : ValueEventListener
        {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                val publications = ArrayList<Publication>()

                for(postSnapshot in p0.children )
                {
                   val p = postSnapshot.value as HashMap<String, String>
                   val userId = p["user_id"]
                   val title = p["title"]
                   val description = p["description"]
                   val id = p["id"]
                    id?.let{
                        val publi = Publication(id, title, description, userId)
                        publications.add(publi)

                      //  val storageRef = storage.reference
                        //val imgRef = storageRef.child(id + "/" + id + ".png")
                      //  var link : String? = null
                       // link = imgRef.getDownloadUrl().toString()
                    }

                }
                publications.reverse()

              recyclerAndDatabaseHandler(publications)

            }

        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_feed)
        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        database = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance()



        downloadPosts()

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
    fun recyclerAndDatabaseHandler(publications : ArrayList<Publication>){
        RecyclerFeed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //database request pour récuperer un objet de type LatestPublication


        // ---------------- EXAMPLE --------------------
       /* val publi = Publication(
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
        tabPublis.results.add(publi2)*/

        // ---------------- EXAMPLE --------------------
        val tabPublis = LatestPublication()
        tabPublis.results = publications
        val adapter = RecyclerAdapterFeed(content = tabPublis, listener = this)
        RecyclerFeed.adapter = adapter

    }



    override fun onSelectPublication(user: Publication?) {
        /*val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)*/
    }
}

