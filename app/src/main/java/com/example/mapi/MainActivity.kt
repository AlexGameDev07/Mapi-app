package com.example.mapi

import ImageAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mapi.Interfaces.ApiClient
import com.example.mapi.Interfaces.ImagesResponse
import com.example.mapi.Interfaces.RequestData

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val typeEditText = findViewById<EditText>(R.id.typeEditText)
        val categoryEditText = findViewById<EditText>(R.id.categoryEditText)
        val btn = findViewById<Button>(R.id.btnChange)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        imageAdapter = ImageAdapter(emptyList())
        recyclerView.adapter = imageAdapter

        btn.setOnClickListener {
            val type = typeEditText.text.toString()
            val category = categoryEditText.text.toString()
            val requestData = RequestData(exclude = emptyList()) // Cambia esto si necesitas excluir URLs

            // Oculta el teclado
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

            ApiClient.apiService.getImages(type, category, requestData).enqueue(object : Callback<ImagesResponse> {
                override fun onResponse(call: Call<ImagesResponse>, response: Response<ImagesResponse>) {
                    if (response.isSuccessful) {
                        val images = response.body()?.files ?: emptyList()
                        imageAdapter = ImageAdapter(images)
                        recyclerView.adapter = imageAdapter
                    } else {
                        // Maneja el error aquí
                    }
                }

                override fun onFailure(call: Call<ImagesResponse>, t: Throwable) {
                    // Maneja el error aquí
                }
            })
        }
    }
}