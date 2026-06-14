package com.example.cadeachadosperdidos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cadeachadosperdidos.R
import com.example.cadeachadosperdidos.model.Objeto

class ObjetoAdapter(
    private var objetos: List<Objeto>,
    private val onEditarClick: (Objeto) -> Unit,
    private val onExcluirClick: (Objeto) -> Unit
) : RecyclerView.Adapter<ObjetoAdapter.ObjetoViewHolder>() {

    class ObjetoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNomeObjeto: TextView = itemView.findViewById(R.id.txtNomeObjeto)
        val txtCategoriaStatus: TextView = itemView.findViewById(R.id.txtCategoriaStatus)
        val txtLocal: TextView = itemView.findViewById(R.id.txtLocal)
        val txtDescricao: TextView = itemView.findViewById(R.id.txtDescricao)
        val txtRecomendacao: TextView = itemView.findViewById(R.id.txtRecomendacao)
        val btnEditar: Button = itemView.findViewById(R.id.btnEditar)
        val btnExcluir: Button = itemView.findViewById(R.id.btnExcluir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjetoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_objeto, parent, false)

        return ObjetoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ObjetoViewHolder, position: Int) {
        val objeto = objetos[position]

        holder.txtNomeObjeto.text = objeto.nome
        holder.txtCategoriaStatus.text = "${objeto.categoria} - ${objeto.status}"
        holder.txtLocal.text = "Local: ${objeto.local ?: "Não informado"}"
        holder.txtDescricao.text = "Descrição: ${objeto.descricao ?: "Não informada"}"
        holder.txtRecomendacao.text = "Recomendação: ${objeto.recomendacao ?: "Sem recomendação"}"

        holder.btnEditar.setOnClickListener {
            onEditarClick(objeto)
        }

        holder.btnExcluir.setOnClickListener {
            onExcluirClick(objeto)
        }
    }

    override fun getItemCount(): Int {
        return objetos.size
    }

    fun atualizarLista(novaLista: List<Objeto>) {
        objetos = novaLista
        notifyDataSetChanged()
    }
}