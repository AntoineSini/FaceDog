package fr.isen.facedog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_personal_page.*
import com.google.firebase.database.DatabaseReference
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class PersonalPageActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_page)
        auth = FirebaseAuth.getInstance()
        
        /*database.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.getValue(User::class.java)
                post?.username.toString()
                post?.email.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                //print error.message
            }
        })
        accessInformations()
        database = FirebaseDatabase.getInstance().reference
        signOutButton.setOnClickListener {
            signOutUser()
        }*/
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser == null){
            auth.signOut()
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
}
