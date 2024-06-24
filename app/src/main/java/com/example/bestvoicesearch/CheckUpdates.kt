package com.example.bestvoicesearch

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bestvoicesearch.databinding.ActivityCheckUpdatesBinding
import com.example.bestvoicesearch.databinding.ActivityMainBinding

class CheckUpdates : AppCompatActivity() {
  private val binding by lazy {
      ActivityCheckUpdatesBinding.inflate(layoutInflater)
  }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.appybuilder.onlinehelp3011.BestVoiceSearch"))
        startActivity(urlIntent)
        finish()
    }
}