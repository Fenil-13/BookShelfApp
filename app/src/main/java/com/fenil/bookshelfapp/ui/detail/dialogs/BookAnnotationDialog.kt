package com.fenil.bookshelfapp.ui.detail.dialogs

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.fenil.bookshelfapp.databinding.DialogAddAnnotationBinding

class BookAnnotationDialog(context: Context,val onSubmissionClick: (text: String)->Unit) : AlertDialog(context) {

    var _binding: DialogAddAnnotationBinding? = null
    val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DialogAddAnnotationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSubmit.setOnClickListener {
            binding.etAnnotation.text?.toString()?.let(onSubmissionClick)
            dismiss()
        }
        binding.etAnnotation.setShowSoftInputOnFocus(true);
        val imm = context.getSystemService(InputMethodManager::class.java)
        imm.showSoftInput(binding.etAnnotation, InputMethodManager.SHOW_IMPLICIT)
    }
}