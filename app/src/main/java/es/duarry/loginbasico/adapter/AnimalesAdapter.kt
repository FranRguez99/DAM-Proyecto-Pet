package es.duarry.loginbasico.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.duarry.loginbasico.Animales
import es.duarry.loginbasico.R

class animalesAdapter(private val listaAnimales: List<Animales>) : RecyclerView.Adapter<AnimalesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AnimalesViewHolder(layoutInflater.inflate(R.layout.item_animales, parent , false))
    }

    override fun onBindViewHolder(holder: AnimalesViewHolder, position: Int) {
        val item = listaAnimales[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = listaAnimales.size

}