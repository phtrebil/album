package com.example.album.ui.activity

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.album.databinding.ActivityPegaImagemBinding

class PegaImagem : AppCompatActivity() {

    companion object{
        private val PERMISSAO_GALERIA = android.Manifest.permission.READ_EXTERNAL_STORAGE
    }
    private val binding by lazy {
        ActivityPegaImagemBinding.inflate(layoutInflater)
    }

    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.galeria.setOnClickListener { buscaImagemNaGaleria() }
    }

    private fun buscaImagemNaGaleria() {

    }

    fun verificaPermissaoGaleria(){
        val permissaoGaleriaAceita = verificaPermissao(PERMISSAO_GALERIA)

        when{
            permissaoGaleriaAceita -> {

            }
            shouldShowRequestPermissionRationale(PERMISSAO_GALERIA) -> showDialogPermissao()
        }else ->
    }
    private fun verificaPermissao(permissao: String){
       ContextCompat.checkSelfPermission(this, permissao) == PackageManager.PERMISSION_GRANTED
    }
    private fun showDialogPermissao(){

    }
}

