package fr.isen.facedog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class NewPostActivity : AppCompatActivity() {

    lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)
        //authentication part
        auth = FirebaseAuth.getInstance()

        //database part
        addToDatabase(database)

    }

    fun addToDatabase(firebaseData: DatabaseReference) {

        auth.currentUser?.uid
        val newPublication = Publication("2", "Test 3", "Antoiiiiiiiiine", auth.currentUser?.uid)
        val key = firebaseData.child("publication").push().key ?: ""
        newPublication.publication_id = key
        firebaseData.child("publication").child(key).setValue(newPublication)
    }
}
