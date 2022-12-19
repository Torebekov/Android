package com.example.movieproject.presentation.view

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.movieproject.R
import com.example.movieproject.presentation.utilities.LoginUtil
import com.example.movieproject.presentation.view_model.LoginViewModel
import com.example.movieproject.presentation.view_model.ViewModelProviderFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var sessionId: TextView
    private lateinit var userLogin: EditText
    private lateinit var userPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton = findViewById(R.id.btn_login)
        userLogin = findViewById(R.id.et_email)
        userPassword = findViewById(R.id.et_password)
        val viewModelProviderFactory = ViewModelProviderFactory(this)
        loginViewModel = ViewModelProvider(this, viewModelProviderFactory).get(
            LoginViewModel::class.java)

        loginButton.setOnClickListener {
            val login = userLogin.text.toString()
            val password = userPassword.text.toString()
            login(login, password)
        }

        loginViewModel.liveData.observe(this, Observer {
            Toast.makeText(this, it.toString(),Toast.LENGTH_SHORT).show()
        })
    }

   fun checkLoginForm(login: String, password: String) : Boolean{
        return LoginUtil.validateLoginForm(login, password)
    }

    private fun login(login: String, password: String){
        if(checkLoginForm(login, password)){
            Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show()
            loginViewModel.getToken(login,password)
        }
        else{
            Toast.makeText(this, "Please write login and password fully", Toast.LENGTH_LONG).show()
        }
    }


}