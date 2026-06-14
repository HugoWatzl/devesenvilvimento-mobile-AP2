package com.example.cadeachadosperdidos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cadeachadosperdidos.adapter.ObjetoAdapter
import com.example.cadeachadosperdidos.api.RetrofitClient
import com.example.cadeachadosperdidos.model.Objeto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListagemActivity : AppCompatActivity() {

    // lateinit é usado porque essas variáveis serão inicializadas depois do setContentView(), quando os componentes do XML já estiverem carregados na tela.
    private lateinit var recycler: RecyclerView
    private lateinit var barra: ProgressBar
    private lateinit var txtVazio: TextView
    private lateinit var btnCadastrar: Button
    private lateinit var btnContato: Button

    private lateinit var objetoAdapter: ObjetoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem)

        recycler = findViewById(R.id.recyclerObjetos)
        barra = findViewById(R.id.progressBarLista)
        txtVazio = findViewById(R.id.txtListaVazia)
        btnCadastrar = findViewById(R.id.btnNovoObjeto)
        btnContato = findViewById(R.id.btnContato)

        iniciarRecycler()
        iniciarBotoes()
    }

    override fun onResume() {
        super.onResume()
        buscarObjetos()
    }

    private fun iniciarRecycler() {
        objetoAdapter = ObjetoAdapter(
            objetos = emptyList(),
            onEditarClick = { objeto ->
                val intent = Intent(this, EditarActivity::class.java)
                intent.putExtra("objeto_id", objeto.id)
                startActivity(intent)
            },
            onExcluirClick = { objeto ->
                excluirObjeto(objeto)
            }
        )

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = objetoAdapter
    }

    private fun iniciarBotoes() {
        btnCadastrar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        btnContato.setOnClickListener {
            val intent = Intent(this, ContatoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun buscarObjetos() {
        barra.visibility = View.VISIBLE
        txtVazio.visibility = View.GONE

        RetrofitClient.apiService.listarObjetos().enqueue(object : Callback<List<Objeto>> {
            override fun onResponse(call: Call<List<Objeto>>, response: Response<List<Objeto>>) {
                barra.visibility = View.GONE

                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()

                    objetoAdapter.atualizarLista(lista)

                    if (lista.isEmpty()) {
                        txtVazio.visibility = View.VISIBLE
                    } else {
                        txtVazio.visibility = View.GONE
                    }

                } else {
                    Toast.makeText(
                        this@ListagemActivity,
                        "Erro ao buscar objetos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Objeto>>, t: Throwable) {
                barra.visibility = View.GONE

                Toast.makeText(
                    this@ListagemActivity,
                    "Erro de conexão: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun excluirObjeto(objeto: Objeto) {
        val id = objeto.id

        if (id == null) {
            Toast.makeText(this, "Objeto sem ID", Toast.LENGTH_SHORT).show()
            return
        }

        RetrofitClient.apiService.deletarObjeto(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@ListagemActivity,
                        "Objeto excluído",
                        Toast.LENGTH_SHORT
                    ).show()

                    buscarObjetos()
                } else {
                    Toast.makeText(
                        this@ListagemActivity,
                        "Erro ao excluir",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(
                    this@ListagemActivity,
                    "Erro de conexão: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}