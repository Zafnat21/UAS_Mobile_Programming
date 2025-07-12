package com.example.uas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = intent.getStringExtra("username") ?: "User"

        val welcomeText = findViewById<TextView>(R.id.welcomeText)
        welcomeText.text = "Hi $username, Welcome"

        val imageView = findViewById<ImageView>(R.id.meditatingImage)
        Glide.with(this)
            .asGif()
            .load(R.drawable.meditating)
            .into(imageView)

        val btnMulai = findViewById<Button>(R.id.btnMulai)
        btnMulai.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }
    }
}
