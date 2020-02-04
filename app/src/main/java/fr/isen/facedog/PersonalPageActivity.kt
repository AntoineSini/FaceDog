package fr.isen.facedog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_personal_page.*

class PersonalPageActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_page)
        auth = FirebaseAuth.getInstance()
        accessInformations()
        database = FirebaseDatabase.getInstance().reference
        signOutButton.setOnClickListener {
            signOutUser()
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser == null){
            signOutUser()
        }
    }

    private fun accessInformations(){
        val menuListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                val user = dataSnapshot.getValue(User::class.java)
            }
            override fun onCancelled(databaseError: DatabaseError){
                Log.w("loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addValueEventListener(menuListener)
    }

    fun signOutUser(){
        auth.signOut()
        //intent = Intent(this, ConnectionActivity::class.java)
        startActivity(intent)
    }
}
