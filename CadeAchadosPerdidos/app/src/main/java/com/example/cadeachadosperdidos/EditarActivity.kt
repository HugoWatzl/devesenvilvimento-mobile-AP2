package com.example.cadeachadosperdidos

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cadeachadosperdidos.api.RetrofitClient
import com.example.cadeachadosperdidos.model.Objeto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditarActivity : AppCompatActivity() {

    private lateinit var editNome: EditText
    private lateinit var spinnerCategoria: Spinner
    private lateinit var radioStatus: RadioGroup
    private lateinit var radioPerdi: RadioButton
    private lateinit var radioEncontrei: RadioButton
    private lateinit var editLocal: EditText
    private lateinit var editDescricao: EditText
    private lateinit var btnSalvar: Button
    private lateinit var btnVoltar: Button

    private var objetoId: Int = -1

    // lateinit é usado porque essas variáveis serão inicializadas depois do setContentView(), quando os componentes do XML já estiverem carregados na tela.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar)

        editNome = findViewById(R.id.editNomeEditar)
        spinnerCategoria = findViewById(R.id.spinnerCategoriaEditar)
        radioStatus = findViewById(R.id.radioStatusEditar)
        radioPerdi = findViewById(R.id.radioPerdiEditar)
        radioEncontrei = findViewById(R.id.radioEncontreiEditar)
        editLocal = findViewById(R.id.editLocalEditar)
        editDescricao = findViewById(R.id.editDescricaoEditar)
        btnSalvar = findViewById(R.id.btnSalvarEdicao)
        btnVoltar = findViewById(R.id.btnVoltarEditar)

        objetoId = intent.getIntExtra("objeto_id", -1)

        if (objetoId == -1) {
            Toast.makeText(this, "Objeto inválido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        configurarSpinner()
        buscarObjeto()

        btnSalvar.setOnClickListener {
            salvarAlteracoes()
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }

    private fun configurarSpinner() {
        val categorias = listOf(
            "Documentos",
            "Eletronicos",
            "Roupas",
            "Acessorios",
            "Outros"
        )

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categorias
        )

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategoria.adapter = adaptador
    }

    private fun buscarObjeto() {
        RetrofitClient.apiService.buscarObjetoPorId(objetoId).enqueue(object : Callback<Objeto> {
            override fun onResponse(call: Call<Objeto>, response: Response<Objeto>) {
                if (response.isSuccessful) {
                    val objeto = response.body()

                    if (objeto != null) {
                        preencherCampos(objeto)
                    }
                } else {
                    Toast.makeText(
                        this@EditarActivity,
                        "Erro ao buscar objeto",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Objeto>, t: Throwable) {
                Toast.makeText(
                    this@EditarActivity,
                    "Erro de conexão: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun preencherCampos(objeto: Objeto) {
        editNome.setText(objeto.nome)
        editLocal.setText(objeto.local)
        editDescricao.setText(objeto.descricao)

        val categorias = listOf(
            "Documentos",
            "Eletronicos",
            "Roupas",
            "Acessorios",
            "Outros"
        )

        val posicaoCategoria = categorias.indexOf(objeto.categoria)

        if (posicaoCategoria >= 0) {
            spinnerCategoria.setSelection(posicaoCategoria)
        }

        if (objeto.status == "Perdi") {
            radioPerdi.isChecked = true
        } else {
            radioEncontrei.isChecked = true
        }
    }

    private fun salvarAlteracoes() {
        val nome = editNome.text.toString()
        val categoria = spinnerCategoria.selectedItem.toString()
        val local = editLocal.text.toString()
        val descricao = editDescricao.text.toString()

        val status = if (radioStatus.checkedRadioButtonId == R.id.radioPerdiEditar) {
            "Perdi"
        } else {
            "Encontrei"
        }

        if (nome.isBlank()) {
            Toast.makeText(
                this,
                "Digite o nome do objeto",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val objeto = Objeto(
            id = objetoId,
            nome = nome,
            categoria = categoria,
            status = status,
            local = local,
            descricao = descricao
        )

        RetrofitClient.apiService.atualizarObjeto(objetoId, objeto)
            .enqueue(object : Callback<Objeto> {
                override fun onResponse(call: Call<Objeto>, response: Response<Objeto>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@EditarActivity,
                            "Objeto atualizado com sucesso",
                            Toast.LENGTH_SHORT
                        ).show()

                        finish()
                    } else {
                        Toast.makeText(
                            this@EditarActivity,
                            "Erro ao atualizar objeto",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Objeto>, t: Throwable) {
                    Toast.makeText(
                        this@EditarActivity,
                        "Erro de conexão: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}