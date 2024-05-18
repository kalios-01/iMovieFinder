package com.kaliostech.imoviefinder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MovieFinder : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var moviename: EditText
    private lateinit var release_year: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_finder)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        button = findViewById(R.id.btn_searchmovie)
        moviename = findViewById(R.id.edt_moviename)
        release_year = findViewById(R.id.edit_release_year)


        button.setOnClickListener {
            val moviename = moviename.text.toString()
            val release_year = release_year.text.toString()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("moviename", moviename)
            intent.putExtra("release_year", release_year)
            startActivity(intent)
        }
    }
}