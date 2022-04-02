package com.ionic.eVote

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

            ref.child("Collected").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = User()
                    if (!dataSnapshot.child("userKey").exists()) {
                        val mapUser = mapOf(panNum to user)
                        ref.child("userKey").setValue(mapUser)
                    } else {
                        val mapUser: ArrayList<Map<String, User>> = ArrayList()
                        for (snapshot in dataSnapshot.child("userKey").children) {
                            val u = snapshot.getValue(User::class.java)!!
                            val m = mapOf(snapshot.key.toString() to u)
                            mapUser.add(m)
                        }
                        ref.child("userKey").setValue(mapUser)
                        Toast.makeText(this@RegisterActivity, "Registered", Toast.LENGTH_LONG)
                            .show()
                    }
                    ref.child("users").removeEventListener(this)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@RegisterActivity, "Error", Toast.LENGTH_LONG).show()
                    Log.d("FirebaseError", error.message)
                }
            })

        }
    }
}