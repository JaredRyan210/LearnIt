package com.hfad.finalproject_team_temp.ui.scoreboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hfad.finalproject_team_temp.R

class ScoreboardAdapter(private var titles: List<String>, private var details: List<String>, private var images: List<Int>) :
RecyclerView.Adapter<ScoreboardAdapter.ViewHolder> () {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.tv_title)
        val itemDetail: TextView = itemView.findViewById(R.id.tv_description)
        val itemPicture: ImageView = itemView.findViewById(R.id.iv_image)

        init {
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                Toast.makeText(itemView.context, "You clicked on item #${position +1}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreboardAdapter.ViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.high_score, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ScoreboardAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemDetail.text = details[position]
        holder.itemPicture.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}