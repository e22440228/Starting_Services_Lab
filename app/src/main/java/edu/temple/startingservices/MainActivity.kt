package edu.temple.startingservices

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val input = findViewById<TextView>(R.id.editTextNumberSigned)
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            val text = input.text?.toString()?.trim()
            var seconds = text?.toIntOrNull()

            if (seconds == null || seconds < 0) {
                Log.i("MainActivity", "Invalid input: '$text'")
                input.error = "Please enter a non-negative number"
                return@setOnClickListener
            }
            val intent = Intent(this, ConutDownServerice::class.java)
                .putExtra(ConutDownServerice.EXTRA_SECONDS, seconds)
            startService(intent)

        }

    }
}