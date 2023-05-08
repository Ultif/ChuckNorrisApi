package com.example.chucknorrisapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.chucknorrisapi.databinding.ActivityMainBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null
    private val arr = arrayOf("animal","career","celebrity","dev","explicit","fashion","food",
        "history","money","movie","music","political","religion","science","sport","travel")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnGetJoke?.setOnClickListener {
            val intent = Intent(this@MainActivity, JokesActivity::class.java)
            val category = binding?.etCategory?.text.toString().lowercase().trim()

            if(arr.any { it ==  category}){
                intent.putExtra(CATEGORY, category)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Choose a category", Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    companion object {
        const val CATEGORY: String = "category"
    }
}