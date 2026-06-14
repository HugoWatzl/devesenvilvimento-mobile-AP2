package com.example.cadeachadosperdidos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cadeachadosperdidos.api.RetrofitClient
import com.example.cadeachadosperdidos.model.Objeto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormularioFragment : Fragment() {

    private lateinit var editNome: EditText
    private lateinit var spinnerCategoria: Spinner
    private lateinit var radioStatus: RadioGroup
    private lateinit var radioPerdi: RadioButton
    private lateinit var editLocal: EditText
    private lateinit var editDescricao: EditText
    private lateinit var btnSalvar: Button
    private lateinit var btnVoltar: Button

    // lateinit é usado porque essas variáveis serão inicializadas depois que o layout do Fragment for carregado.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val tela = inflater.inflate(R.layout.fragment_formulario, container, false)

        editNome = tela.findViewById(R.id.editNome)
        spinnerCategoria = tela.findViewById(R.id.spinnerCategoria)
        radioStatus = tela.findViewById(R.id.radioStatus)
        radioPerdi = tela.findViewById(R.id.radioPerdi)
        editLocal = tela.findViewById(R.id.editLocal)
        editDescricao = tela.findViewById(R.id.editDescricao)
        btnSalvar = tela.findViewById(R.id.btnSalvar)
        btnVoltar = tela.findViewById(R.id.btnVoltarCadastro)

        configurarSpinner()

        radioPerdi.isChecked = true

        btnSalvar.setOnClickListener {
            salvarObjeto()
        }

        btnVoltar.setOnClickListener {
            requireActivity().finish()
        }

        return tela
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
            requireContext(),
            android.R.layout.simple_spinner_item,
            categorias
        )

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategoria.adapter = adaptador
    }

    private fun salvarObjeto() {
        val nome = editNome.text.toString()
        val categoria = spinnerCategoria.selectedItem.toString()
        val local = editLocal.text.toString()
        val descricao = editDescricao.text.toString()

        val status = if (radioStatus.checkedRadioButtonId == R.id.radioPerdi) {
            "Perdi"
        } else {
            "Encontrei"
        }

        if (nome.isBlank()) {
            Toast.makeText(
                requireContext(),
                "Digite o nome do objeto",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val objeto = Objeto(
            nome = nome,
            categoria = categoria,
            status = status,
            local = local,
            descricao = descricao
        )

        RetrofitClient.apiService.criarObjeto(objeto).enqueue(object : Callback<Objeto> {
            override fun onResponse(call: Call<Objeto>, response: Response<Objeto>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Objeto cadastrado com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()

                    requireActivity().finish()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Erro ao cadastrar objeto",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Objeto>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Erro de conexão: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}