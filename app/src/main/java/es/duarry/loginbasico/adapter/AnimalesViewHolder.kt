package es.duarry.loginbasico.adapter

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.duarry.loginbasico.Animales

import es.duarry.loginbasico.databinding.ItemAnimalesBinding

class AnimalesViewHolder(view:View):RecyclerView.ViewHolder(view) {
    val binding = ItemAnimalesBinding.bind(view)

    fun render(animalesModel: Animales){
        binding.tvNombre.text = animalesModel.nombre
        binding.tvCodigo.text = animalesModel.cod.toString()
        Glide.with(binding.ivAnimales.context).load(animalesModel.foto).into(binding.ivAnimales)
        itemView.setOnClickListener(){
            Toast.makeText(binding.ivAnimales.context, "Raza: " + animalesModel.raza +" Fecha Nac: " + animalesModel.fechNac + " Sexo: " +animalesModel.sexo,Toast.LENGTH_SHORT).show()
        }
    }
}