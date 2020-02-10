package fr.isen.facedog

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.isen.facedog.classes.LatestPublication
import fr.isen.facedog.classes.Publication
import kotlinx.android.synthetic.main.item_publication.view.*

//import com.squareup.picasso.Picasso

class RecyclerAdapterFeed(val content: LatestPublication, val listener: OnPublicationRecycleListener) : RecyclerView.Adapter<RecyclerAdapterFeed.FeedViewHolder>(){
    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        content.results?.let {
            holder.bind(it[position])
        }
    }
    override fun getItemCount(): Int {
        return content.results?.count()?: 0
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_publication, parent,false) as View
        view.setOnClickListener{
            listener.onSelectPublication(it.tag as? Publication)
        }
        return FeedViewHolder(view)
    }
    class FeedViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bind(content: Publication?){
            view.titleTextView.text = content?.title
            view.descriptionTextView.text = content?.description
            view.tag = content
        }
    }
    interface OnPublicationRecycleListener{
        fun onSelectPublication(user: Publication?)
    }
}
