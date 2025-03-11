package com.example.mapi

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mapi.Interfaces.ApiClient
import com.example.mapi.Interfaces.ImageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtType = findViewById<TextView>(R.id.txtType)
        val txtCategory = findViewById<TextView>(R.id.txtCategory)
        val btn = findViewById<Button>(R.id.btnChange)
        val imageView = findViewById<ImageView>(R.id.img)

        btn.setOnClickListener {
            val type = txtType.text.toString()
            val category = txtCategory.text.toString()

            // Ocultar el teclado
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

            ApiClient.apiService.getImage(type, category).enqueue(object : Callback<ImageResponse> {
                override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                    if (response.isSuccessful) {
                        val imageUrl = response.body()?.url
                        Glide.with(this@MainActivity).load(imageUrl).into(imageView)
                    } else {
                        // Maneja el error aquí
                    }
                }

                override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                    // Maneja el error aquí
                }
            })
        }
    }
}