package com.ionic.eVote

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.content.ContextCompat
import com.ionic.eVote.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private var binding: ActivityRegisterBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)

        binding!!.layPanNum.hintTextColor = ContextCompat.getColorStateList(this, R.color.black)
        binding!!.layFullName.hintTextColor = ContextCompat.getColorStateList(this, R.color.black)
        binding!!.layGender.hintTextColor = ContextCompat.getColorStateList(this, R.color.black)
        binding!!.layMobile.hintTextColor = ContextCompat.getColorStateList(this, R.color.black)

        val arrGender = listOf("Male", "Female", "Other")
        val adapter = ArrayAdapter(this, R.layout.list_gender, arrGender)
        ((binding!!.layGender.editText) as AppCompatAutoCompleteTextView).setAdapter(adapter)
    }
}