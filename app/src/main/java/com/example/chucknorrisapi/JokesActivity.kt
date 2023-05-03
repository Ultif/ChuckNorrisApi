package com.example.chucknorrisapi

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.chucknorrisapi.databinding.ActivityJokesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Url

class JokesActivity : AppCompatActivity() {

    private var binding : ActivityJokesBinding? = null
    private lateinit var jokeCategory : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJokesBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.tbJokes)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.tbJokes?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        jokeCategory = intent.extras?.getString(MainActivity.CATEGORY) as String
        Log.i("cat", jokeCategory.toString())

        getJoke()

        binding?.btnGenerateMore?.setOnClickListener {
            getJoke()
        }
    }

    private fun getJoke(){
        val retrofit = Retrofit.Builder().baseUrl("https://api.chucknorris.io/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val service : ChuckJokesService = retrofit.create(ChuckJokesService::class.java)

        val call : Call<ChuckNorrisResponse> = service.getRandomFactByCategory(jokeCategory)

        call.enqueue(object : Callback<ChuckNorrisResponse> {
            override fun onResponse(
                call: Call<ChuckNorrisResponse>,
                response: Response<ChuckNorrisResponse>
            ) {
                if(response.isSuccessful){
                    val joke: ChuckNorrisResponse = response.body() as ChuckNorrisResponse

                    binding?.tvJoke?.text = joke.value

                    displayChuckImage(joke.icon_url)

                    Log.i("Joke", "${joke.value}")
                }else{
                    when(response.code()){
                        400 -> {
                            Log.e("Error 400", "Bad Connection")
                        }
                        404 -> {
                            Log.e("Error 404", "Not Found")
                        }

                        429 -> {
                            Log.e("Error 429", "Too Many Requests")
                        }

                        else -> {
                            Log.e("Error", "Generic Error")
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ChuckNorrisResponse>, t: Throwable) {
                Log.e("Error", "${t.message}")
            }

        })
    }

    private fun displayChuckImage(url : String){
        Glide.with(this).load(url).into(binding?.ivIcon as ImageView)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}