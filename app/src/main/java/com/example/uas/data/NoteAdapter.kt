package com.example.uas.data

import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uas.databinding.ItemNoteBinding

class NoteAdapter(
    private var notes: List<NoteEntity>,
    private val onItemClick: (NoteEntity) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NoteEntity, position: Int) {
            binding.textViewContent.text = note.content

            // 🌈 Warna pastel berbeda untuk tiap card
            val colors = listOf("#FFF9C4", "#FFECB3", "#B2EBF2", "#C8E6C9")
            val bgColor = Color.parseColor(colors[position % colors.size])
            binding.root.setCardBackgroundColor(bgColor)

            // ✅ Ellipsize
            binding.textViewContent.maxLines = 4
            binding.textViewContent.ellipsize = TextUtils.TruncateAt.END

            // 🔥 Set onClick
            binding.root.setOnClickListener {
                onItemClick(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position], position)
    }

    override fun getItemCount(): Int = notes.size

    fun setNotes(newNotes: List<NoteEntity>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}
