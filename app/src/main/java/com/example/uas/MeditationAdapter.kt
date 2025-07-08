package com.example.uas

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlin.text.isEmpty

class MeditationAdapter(
    private val context: Context,
    private val meditationItems: List<MeditationItem>,
    private val onPlayButtonClick: (Int) -> Unit // Callback untuk menangani klik tombol play
) : RecyclerView.Adapter<MeditationAdapter.ViewHolder>() {

    // Menyimpan posisi item yang sedang diputar
    private var currentPlayingPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meditation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = meditationItems[position]

        holder.titleText.text = item.title
        holder.subtitleText.text = item.subtitle

        // Menyembunyikan subtitle jika kosong
        holder.subtitleText.visibility = if (item.subtitle.isEmpty()) View.GONE else View.VISIBLE

        // Mengatur item agar mengambil lebar penuh jika isFullWidth adalah true
        val layoutParams = holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
        layoutParams.isFullSpan = item.isFullWidth

        // Reset background terlebih dahulu
        holder.cardBackground.background = null
        holder.backgroundImage.visibility = View.GONE
        holder.gradientOverlay.visibility = View.GONE

        // Mengatur gambar latar belakang dan overlay gradient
        if (item.backgroundImage != null) {
            // Tampilkan gambar latar belakang dengan pengaturan optimal
            holder.backgroundImage.setImageResource(item.backgroundImage)
            holder.backgroundImage.visibility = View.VISIBLE
            holder.backgroundImage.scaleType = ImageView.ScaleType.CENTER_CROP

            // Tambahkan gradient overlay yang minimal untuk keterbacaan teks
            setMinimalGradientOverlay(holder.gradientOverlay)
            holder.gradientOverlay.visibility = View.VISIBLE

            // Hapus background dari cardBackground agar gambar terlihat penuh
            holder.cardBackground.background = null
        } else {
            // Jika tidak ada gambar, gunakan gradient background
            holder.backgroundImage.visibility = View.GONE
            holder.gradientOverlay.visibility = View.GONE
            setBackgroundGradient(holder.cardBackground, item.type)
        }

        // Mengatur icon tombol play/pause berdasarkan status
        if (currentPlayingPosition == position) {
            holder.playButton.setImageResource(R.drawable.ic_pause) // Gunakan icon pause
        } else {
            holder.playButton.setImageResource(R.drawable.ic_play) // Gunakan icon play
        }

        // Menangani klik tombol play
        holder.playButton.setOnClickListener {
            onPlayButtonClick(position)
        }
    }

    override fun getItemCount(): Int = meditationItems.size

    // Fungsi untuk mengupdate status tombol play/pause
    fun updatePlayingState(position: Int, isPlaying: Boolean) {
        val previousPosition = currentPlayingPosition

        if (isPlaying) {
            currentPlayingPosition = position
        } else {
            currentPlayingPosition = -1
        }

        // Notify perubahan untuk posisi yang terpengaruh
        if (previousPosition != -1) {
            notifyItemChanged(previousPosition)
        }
        if (position != -1) {
            notifyItemChanged(position)
        }
    }

    // Gradient overlay minimal hanya untuk keterbacaan teks
    private fun setMinimalGradientOverlay(overlay: View) {
        val gradient = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = context.resources.getDimension(R.dimen.card_corner_radius)
        }

        // Gradient minimal - hanya untuk keterbacaan teks
        val colors = intArrayOf(
            0x20000000.toInt(), // Sangat tipis di atas (12% opacity)
            0x00000000.toInt(), // Transparan penuh di tengah
            0x40000000.toInt()  // Sedikit gelap di bawah untuk play button (25% opacity)
        )

        gradient.colors = colors
        gradient.orientation = GradientDrawable.Orientation.TOP_BOTTOM
        overlay.background = gradient
    }

    // Background gradient untuk item tanpa gambar
    private fun setBackgroundGradient(background: FrameLayout, type: String) {
        val gradient = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = context.resources.getDimension(R.dimen.card_corner_radius)
        }

        val colors = when (type) {
            "daily" -> intArrayOf(0xFF3498DB.toInt(), 0xFF2980B9.toInt())
            "calm" -> intArrayOf(0xFF9B59B6.toInt(), 0xFF8E44AD.toInt())
            "stress" -> intArrayOf(0xFFE74C3C.toInt(), 0xFFC0392B.toInt())
            "nature1" -> intArrayOf(0xFF2ECC71.toInt(), 0xFF27AE60.toInt())
            "nature2" -> intArrayOf(0xFF16A085.toInt(), 0xFF138D75.toInt())
            else -> intArrayOf(0xFF95A5A6.toInt(), 0xFF7F8C8D.toInt())
        }

        gradient.colors = colors
        gradient.orientation = GradientDrawable.Orientation.TOP_BOTTOM
        background.background = gradient
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.titleText)
        val subtitleText: TextView = itemView.findViewById(R.id.subtitleText)
        val playButton: ImageView = itemView.findViewById(R.id.playButton)
        val cardBackground: FrameLayout = itemView.findViewById(R.id.cardBackground)
        val backgroundImage: ImageView = itemView.findViewById(R.id.backgroundImage)
        val gradientOverlay: View = itemView.findViewById(R.id.gradientOverlay)
    }
}