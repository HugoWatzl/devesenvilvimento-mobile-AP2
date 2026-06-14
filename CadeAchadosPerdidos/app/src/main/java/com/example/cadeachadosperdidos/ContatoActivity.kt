package com.example.cadeachadosperdidos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ContatoActivity : AppCompatActivity() {

    private lateinit var btnEmail: Button
    private lateinit var btnTelefone: Button
    private lateinit var btnVoltar: Button

    // lateinit é usado porque essas variáveis serão inicializadas depois do setContentView(), quando os componentes do XML já estiverem carregados na tela.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato)

        btnEmail = findViewById(R.id.btnEmail)
        btnTelefone = findViewById(R.id.btnTelefone)
        btnVoltar = findViewById(R.id.btnVoltar)

        btnEmail.setOnClickListener {
            abrirEmail()
        }

        btnTelefone.setOnClickListener {
            abrirTelefone()
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }

    private fun abrirEmail() {
        val email = "cade@admin.ibmec.edu.br"
        val assunto = "Suporte - App Cadê?"
        val mensagem = "Olá, gostaria de informações sobre um objeto perdido."

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, assunto)
            putExtra(Intent.EXTRA_TEXT, mensagem)
        }

        try {
            startActivity(Intent.createChooser(intent, "Escolha um aplicativo de e-mail"))
        } catch (e: Exception) {
            Toast.makeText(this, "Nenhum aplicativo de e-mail encontrado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun abrirTelefone() {
        val telefone = "21999999999"

        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$telefone")
        }

        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Nenhum aplicativo de telefone encontrado", Toast.LENGTH_SHORT).show()
        }
    }
}