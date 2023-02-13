package com.myprojects.acronymexpander.view

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.myprojects.acronymexpander.R
import com.myprojects.acronymexpander.adapter.MyAdapter
import com.myprojects.acronymexpander.databinding.ActivityAcronymBinding
import com.myprojects.acronymexpander.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private val viewModel: MyViewModel by lazy {
        ViewModelProvider(this)[MyViewModel::class.java]
    }
    private lateinit var binding: ActivityAcronymBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_acronym)
        binding.viewModel = viewModel
        binding.searchBar.filters = binding.searchBar.filters + InputFilter.AllCaps()
        setUpAdapter()
        observe()
    }

    private fun observe(){
        viewModel.onError.observe(this){
            if(it > 0){
                dismissKeyBoard()
                AlertDialog.Builder(this).setMessage(it).setPositiveButton(R.string.ok)
                { _, _ -> viewModel.clearError() }.show()
            }
        }

        viewModel.fullForms.observe(this){
            dismissKeyBoard()
            (binding.recyclerView.adapter as? MyAdapter)?.refresh(it)
                ?: kotlin.run {
                    binding.recyclerView.adapter = MyAdapter(it)
                }
        }
    }

    private fun setUpAdapter(){
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
        }
    }

    private fun dismissKeyBoard(){
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE ) as InputMethodManager
        if(inputMethodManager.isAcceptingText)
            inputMethodManager.hideSoftInputFromWindow( this.currentFocus?.windowToken, 0)
    }
}