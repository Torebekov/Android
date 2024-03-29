package com.example.movie.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.movie.R
import com.example.movie.model.authorization.LoginActivity
import com.example.movie.model.authorization.LoginSharedPref


class UserFragment : Fragment() {
    private lateinit var logOut:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = LayoutInflater.from(container?.context)
            .inflate(R.layout.fragment_user, container, false)
        logOut = view?.findViewById(R.id.logOut)!!
        logOut.setOnClickListener(View.OnClickListener {
            LoginSharedPref().clearUserName(requireActivity())
            val intent = Intent(this.context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

            startActivity(intent)
        })

        return view
    }
}
