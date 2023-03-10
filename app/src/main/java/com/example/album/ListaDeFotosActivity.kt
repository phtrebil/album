package com.example.album

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.album.databinding.ActivityListaDeFotosBinding

class ListaDeFotosActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityListaDeFotosBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_de_fotos)

        binding.fabAddImagem.setOnClickListener {
            val intent = Intent(this, PegaImagem::class.java)
            startActivity(intent)
        }
    }
}