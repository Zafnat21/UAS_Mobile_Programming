package com.example.uas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.uas.dao.AppDatabase
import kotlinx.coroutines.launch

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val emailInput = findViewById<EditText>(R.id.et_email)
        val passwordInput = findViewById<EditText>(R.id.et_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnRegister = findViewById<Button>(R.id.btn_register)

        val userDao = AppDatabase.getDatabase(this).userDao()

        btnLogin.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    val user = userDao.login(email, password)
                    runOnUiThread {
                        if (user != null) {
                            Toast.makeText(
                                this@SignInActivity,
                                "Login berhasil! Selamat datang, ${user.username}",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(this@SignInActivity, MainActivity::class.java)
                            intent.putExtra("username", user.username)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@SignInActivity,
                                "Email atau password salah",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Masukkan email dan password", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}
