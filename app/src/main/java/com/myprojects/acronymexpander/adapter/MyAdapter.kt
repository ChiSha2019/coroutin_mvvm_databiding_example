package com.myprojects.acronymexpander.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.myprojects.acronymexpander.R
import com.myprojects.acronymexpander.databinding.FullFormItemBinding
import com.myprojects.data.FullForm

class MyAdapter(private var fullForms: List<FullForm>): RecyclerView.Adapter<MyAdapter.FullFormViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullFormViewHolder {
        val binding = DataBindingUtil.inflate<FullFormItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.full_form_item,
            parent,
            false)
        return FullFormViewHolder(binding)
    }

    override fun getItemCount() = fullForms.size

    override fun onBindViewHolder(holder: FullFormViewHolder, position: Int) {
        holder.bind(fullForms[position])
    }

    fun refresh(fullForms: List<FullForm>){
        this.fullForms = fullForms
        notifyDataSetChanged()
    }

    inner class FullFormViewHolder(private val binding: FullFormItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fullForm: FullForm){
            binding.fullform = fullForm
        }
    }
}