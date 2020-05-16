package com.example.movie.model.authorization

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.movie.view.activity.MainActivity
import com.example.movie.R
import com.example.movie.view_model.LoginViewModel
import com.example.movie.view_model.ViewModelProviderFactory
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase



class LoginActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var btnlogin: Button
    lateinit var preferences: SharedPreferences
    private lateinit var progressBar: ProgressBar
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        firebaseAnalytics = Firebase.analytics
        val viewModelProviderFactory = ViewModelProviderFactory(this)
        loginViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(LoginViewModel::class.java)
        email = findViewById(R.id.tv_login)
        password = findViewById(R.id.tv_psw)
        btnlogin = findViewById(R.id.b_login)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE
        if (LoginSharedPref().getUserName(this)?.length == 0) {
            // call Login Activity
        } else {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)

        }
        preferences =
            getSharedPreferences("tkn", Context.MODE_PRIVATE)
        btnlogin.setOnClickListener {
            loginViewModel.getTkn()
            loginViewModel.liveData.observe(
                this,
                androidx.lifecycle.Observer { result ->
                    when (result) {
                        is LoginViewModel.State.ShowLoading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                        is LoginViewModel.State.HideLoading -> {
                            progressBar.visibility = View.GONE
                        }
                        is LoginViewModel.State.StartLogin -> {
                            loginViewModel.login2(
                                email = email.text.toString(),
                                password = password.text.toString(),
                                requestedToken = result.token
                            )

                        }
                        is LoginViewModel.State.ShowActivity -> {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                        is LoginViewModel.State.SaveUser -> {
                            LoginSharedPref().setUserName(this@LoginActivity, email.text.toString())

                        }
                        is LoginViewModel.State.GetSession -> {
                            loginViewModel.getSessionId(result.token)
                        }
                        is LoginViewModel.State.SaveSession -> {
                            val edt = preferences.edit()
                            edt.putString("sessionID", result.sessionId)
                            edt.apply()
                        }
                    }

                })
            var bundle:Bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.METHOD, email.text.toString())
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle)
        }
    }
}

