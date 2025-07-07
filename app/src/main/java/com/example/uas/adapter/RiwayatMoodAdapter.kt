package com.example.uas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.uas.R
import com.example.uas.entity.MoodEntity

class RiwayatMoodAdapter(
    private var moods: List<MoodEntity>,
    private val onDeleteClicked: (MoodEntity) -> Unit
) : RecyclerView.Adapter<RiwayatMoodAdapter.MoodViewHolder>() {

    inner class MoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
        val tvMood: TextView = itemView.findViewById(R.id.tvMood)
        val imgMood: ImageView = itemView.findViewById(R.id.imgMood)
        val container: View = itemView.findViewById(R.id.containerItemMood)
        val btnDelete: ImageView = itemView.findViewById(R.id.btnDeleteMood)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_riwayat_mood, parent, false)
        return MoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
        val mood = moods[position]
        holder.tvTanggal.text = mood.date
        holder.tvMood.text = mood.mood

        val context = holder.itemView.context
        holder.container.backgroundTintList = ContextCompat.getColorStateList(context, mood.colorResId)
        holder.imgMood.setImageResource(mood.iconResId)

        holder.btnDelete.setOnClickListener {
            onDeleteClicked(mood)
        }
    }

    override fun getItemCount(): Int = moods.size

    fun setMoods(moods: List<MoodEntity>) {
        this.moods = moods
        notifyDataSetChanged()
    }
}
