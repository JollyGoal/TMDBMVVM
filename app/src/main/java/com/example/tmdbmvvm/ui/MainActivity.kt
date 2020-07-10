package com.example.tmdbmvvm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tmdbmvvm.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_test.setOnClickListener{
            val intent = Intent(this, SingleMovieActivity::class.java)
            intent.putExtra("id", 299534)
            this.startActivity(intent)
        }
    }
}