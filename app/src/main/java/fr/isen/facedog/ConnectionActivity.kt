package fr.isen.facedog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_connection.*

class ConnectionActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)
        //Initialize
        auth = FirebaseAuth.getInstance()

        signUpTextview.setOnClickListener{
            intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
        signInButton.setOnClickListener{
            signIn(emailTextEdit.text.toString(), passwordTextEdit.text.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            intent = Intent(this, GeneralFeedActivity::class.java)
            startActivity(intent)
        }
    }

    fun signIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("antoine", "signInWithEmail:success")
                val user = auth.currentUser
                intent = Intent(this, GeneralFeedActivity::class.java)
                //intent.putExtra(user)
                startActivity(intent)
            } else {
                // If sign in fails, display a message to the user.
                Log.w("antoine", "signInWithEmail:failure", task.exception)
                Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
