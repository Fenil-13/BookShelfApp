package com.fenil.bookshelfapp.DelayAwareClickListener

import android.content.Context
import android.widget.EditText
import android.widget.Toast

fun Context.showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun EditText.isEmpty() = this.text.toString().isEmpty()