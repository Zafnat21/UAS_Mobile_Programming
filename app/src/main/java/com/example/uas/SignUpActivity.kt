package com.example.uas

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.uas.dao.AppDatabase
import com.example.uas.entity.UserEntity
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val usernameInput = findViewById<EditText>(R.id.et_username)
        val emailInput = findViewById<EditText>(R.id.et_email)
        val passwordInput = findViewById<EditText>(R.id.et_password)
        val btnCreateAccount = findViewById<Button>(R.id.btn_create_account)

        val userDao = AppDatabase.getDatabase(this).userDao()

        btnCreateAccount.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    val existing = userDao.getUserByEmail(email)
                    if (existing != null) {
                        runOnUiThread {
                            Toast.makeText(this@SignUpActivity, "Email sudah terdaftar", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        userDao.insertUser(UserEntity(username = username, email = email, password = password))
                        runOnUiThread {
                            Toast.makeText(this@SignUpActivity, "Akun berhasil dibuat", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Lengkapi semua data!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
