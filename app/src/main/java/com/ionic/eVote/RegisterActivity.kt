package com.ionic.eVote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ionic.eVote.databinding.ActivityRegisterBinding
import com.ionic.eVote.models.User

class RegisterActivity : AppCompatActivity() {

    private var binding: ActivityRegisterBinding? = null
    private lateinit var d: User

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

        val database =
            FirebaseDatabase.getInstance("https://e-voteweb-default-rtdb.firebaseio.com/")
        val ref = database.reference

        binding!!.btnContinue.setOnClickListener {
            val panNum = (binding!!.layPanNum.editText as TextInputEditText).text.toString()
            val name = (binding!!.layFullName.editText as TextInputEditText).text.toString()
            val gender =
                (binding!!.layGender.editText as AppCompatAutoCompleteTextView).text.toString()
            val mobile = (binding!!.layMobile.editText as TextInputEditText).text.toString()
            val key = (binding!!.layKey.editText as TextInputEditText).text.toString()

            ref.child("Collected Data").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.child(key).exists()) {
                        d = dataSnapshot.child(key).getValue(User::class.java)!!
                    }
                    if (d.panNum == panNum) {
                        if (name == d.name) {
                            ref.child("users").child(key).setValue(d)
                            ref.child("Collected Data").child(key).removeValue()
                            Toast.makeText(
                                this@RegisterActivity,
                                "Registered Successfully",
                                Toast.LENGTH_LONG
                            ).show()
                            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@RegisterActivity, "Error", Toast.LENGTH_LONG).show()
                    Log.d("FirebaseError", error.message)
                }
            })

        }
    }
}