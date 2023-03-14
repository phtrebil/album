package com.example.album.ui.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.album.databinding.ActivityPegaImagemBinding

class PegaImagem() : AppCompatActivity() {

    companion object {
        private val PERMISSAO_GALERIA = android.Manifest.permission.READ_EXTERNAL_STORAGE
    }


    private val binding by lazy {
        ActivityPegaImagemBinding.inflate(layoutInflater)
    }

    private lateinit var dialog: AlertDialog

    private val requestGaleria =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissao ->
            if (permissao) {
                resultGaleria.launch(
                    Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                )
            } else {
                mostraDialogDePermissao()
            }

        }

    private val resultGaleria =

        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.data?.data != null) {
                val bitmap: Bitmap = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(
                        baseContext.contentResolver,
                        result.data?.data
                    )
                } else {
                    val fonte = ImageDecoder.createSource(
                        this.contentResolver,
                        result.data?.data!!
                    )
                    ImageDecoder.decodeBitmap(fonte)
                }
                binding.foto.setImageBitmap(bitmap)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.galeria.setOnClickListener { verificaPermissaoGaleria() }
        binding.camera.setOnClickListener { mostraCamera() }
    }

    private fun mostraCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(intent)
    }


    private fun verificaPermissaoGaleria() {
        val permissaoGaleriaAceita = verificaPermissao(PERMISSAO_GALERIA)

        when {
            permissaoGaleriaAceita -> {

                resultGaleria.launch(
                    Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                )
            }
            shouldShowRequestPermissionRationale(PERMISSAO_GALERIA) -> mostraDialogDePermissao()

            else -> requestGaleria.launch(PERMISSAO_GALERIA)

        }

    }


    private fun verificaPermissao(permissao: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permissao
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun mostraDialogDePermissao() {
        val builde = AlertDialog.Builder(this)
            .setTitle("Atenção")
            .setMessage("Precisamos do acesso a galeria do dispositivo, deseja permitir agora?")
            .setNegativeButton("Não") { _, _
                ->
                dialog.dismiss()
            }
            .setPositiveButton("Sim") { _, _ ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("packge", packageName, null)
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                dialog.dismiss()
            }
        dialog = builde.create()
        dialog.show()
    }
}

