package fr.isen.facedog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.facedog.classes.LatestPublication
import fr.isen.facedog.classes.Publication
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.item_publication.view.*

//TODO: replace LatestPubli by comments later
class RecyclerAdapterComments(val content: LatestPublication): RecyclerView.Adapter<RecyclerAdapterComments.CommentsViewHolder>(){
    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        content.results.let {
            holder.bind(it[position])
        }
    }
    override fun getItemCount(): Int {
        return content.results.count()?: 0
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_publication, parent,false) as View

        return CommentsViewHolder(view)
    }
    class CommentsViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bind(content: Publication?){
            view.userTextView.text = content?.user_id
            view.descriptionTextView.text = content?.description
        }
    }

}