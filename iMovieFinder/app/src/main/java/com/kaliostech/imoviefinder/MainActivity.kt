package com.kaliostech.imoviefinder

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.kaliostech.imoviefinder.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var poster: ImageView
    private lateinit var textIp: TextView

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val moviename = intent.getStringExtra("moviename")
        val releaseYear = intent.getStringExtra("release_year")?.takeIf { it.isNotEmpty() }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    moviename?.let {
                        RetrofitInstance.api.getMovieInfo(
                            apiKey = "ca0aa54d",
                            title = it,
                            year = releaseYear,
                            plot = "full"
                        )
                    }
                }

                response?.let {
                    if (it.isSuccessful) {
                        val movieInfo = it.body()
                        if (movieInfo != null) {
                            binding.title.text = movieInfo.Title
                            binding.releasedDate.text = "Released: ${movieInfo.Released}"
                            binding.duration.text = "Duration : ${movieInfo.Runtime}"
                            binding.imdbRating.text = "IMDB Rating: ${movieInfo.imdbRating}"
                            binding.poster.load(movieInfo.Poster)
                            binding.Genre.text = "Genre: ${movieInfo.Genre}"
                            binding.language.text = "Language: ${movieInfo.Language}"
                            binding.plot.text = "Plot: ${movieInfo.Plot}"
                            binding.director.text = "Director: ${movieInfo.Director}"
                            binding.writer.text = "Writer: ${movieInfo.Writer}"
                            binding.actor.text = "Actors: ${movieInfo.Actors}"
                            binding.awards.text = "Awards: ${movieInfo.Awards}"
                        } else {
                            binding.plot.text = "Movie details not found"
                        }
                    } else {
                        binding.plot.text = "Error: ${it.message()}"
                    }
                } ?: run {
                    binding.plot.text = "Movie details not found"
                }
            } catch (e: Exception) {
                binding.plot.text = "Error: ${e.message}"
            }
        }
    }
}
