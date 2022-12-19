package com.example.movieproject.presentation.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movieproject.R
import com.example.movieproject.data.models.Singleton
import com.example.movieproject.domain.model.User
import com.example.movieproject.presentation.view.LoginActivity
import com.example.movieproject.presentation.view.MainActivity
import com.example.movieproject.presentation.view_model.MainFragmentViewModel
import com.example.movieproject.presentation.view_model.ProfileViewModel
import com.example.movieproject.presentation.view_model.ViewModelProviderFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_detail.*
import java.lang.reflect.Type

class ProfileFragment : Fragment() {
    private lateinit var userLogoutImageView: ImageView
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var preferences: SharedPreferences
    private lateinit var userAvatarImageView: ImageView
    private lateinit var userName: TextView
    private lateinit var userNameLogin: TextView
    private lateinit var cardViewLogin: CardView
    private lateinit var cardViewUserInfo: CardView
    private lateinit var loginTextView: TextView
    private var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.profile_fragment, container, false)

        val viewModelProviderFactory = ViewModelProviderFactory(context = this.activity as Context)
        profileViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(ProfileViewModel::class.java)

        userLogoutImageView = (rootView as ViewGroup).findViewById(R.id.logout_user)
        userName = (rootView as ViewGroup).findViewById(R.id.user_name)
        userNameLogin = (rootView as ViewGroup).findViewById(R.id.login_user_name)
        userAvatarImageView = (rootView as ViewGroup).findViewById(R.id.user_avatar)
        cardViewLogin = (rootView as ViewGroup).findViewById(R.id.cv)
        cardViewUserInfo = (rootView as ViewGroup).findViewById(R.id.cv_detail_info)
        loginTextView = (rootView as ViewGroup).findViewById(R.id.login)

        loginTextView.setOnClickListener {
            val intent = Intent(activity as Context, LoginActivity::class.java)
            context?.startActivity(intent)
        }

        profileViewModel.liveData.observe(this, Observer {
            if(it.toString()=="Good"){
                val intent = Intent(activity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            else{
                Toast.makeText(context, "Something is wrong", Toast.LENGTH_LONG).show()
            }
        })

        userLogoutImageView.setOnClickListener {
            profileViewModel.logout()
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkIntent()
    }

    private fun checkIntent(){
        val intent = activity?.intent?.extras?.getString("name")
        preferences = context?.getSharedPreferences("Username",0)!!
        val json: String? = preferences.getString("user", null)
        if(intent.isNullOrEmpty()){
            if(json.isNullOrEmpty()){
                cardViewUserInfo.visibility = View.GONE
            }
            else{
                try {
                    val gsonGen = Gson()
                    val type: Type = object : TypeToken<User>() {}.type
                    val user = gsonGen.fromJson<User>(json, type)
                    loadInfoFromLocal(user)
                }catch (e:Exception){}
            }

        }
        else{
            cardViewLogin.visibility = View.GONE
            userName.text = activity?.intent?.extras?.getString("name")
            userNameLogin.text = activity?.intent?.extras?.getString("userName")
            val url = "https://image.tmdb.org/t/p/w500" + activity?.intent?.extras?.getString("avatar_path")
            try {
                Glide.with(activity as Context)
                    .load(url)
                    .into(userAvatarImageView)
            }catch (e:Exception){
                Toast.makeText(context, e.toString(),Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun loadInfoFromLocal(user: User){
        var MySingleton =
            Singleton.create(
                user.username,
                user.sessionId,
                user.accountId
            )
        cardViewLogin.visibility = View.GONE
        userName.text = user.name
        userNameLogin.text = user.username
        val url = "https://image.tmdb.org/t/p/w500" + user.avatar_path
        try {
            Glide.with(activity as Context)
                .load(url)
                .into(userAvatarImageView)
        }catch (e:Exception){
            Toast.makeText(context, e.toString(),Toast.LENGTH_LONG).show()
        }
    }
}