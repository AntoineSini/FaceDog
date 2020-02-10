package fr.isen.facedog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_comments.*

class CommentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        //recyclerAndDatabaseHandler()
    }

    fun recyclerAndDatabaseHandler(){
        RecyclerComments.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //val adapter = RecyclerAdapterComments(content = tabComments, listener = this)
        //RecyclerComments.adapter = adapter
    }
}
