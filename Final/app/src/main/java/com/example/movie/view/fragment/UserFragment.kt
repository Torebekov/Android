package com.example.movie.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.movie.R
import com.example.movie.model.authorization.LoginActivity
import com.example.movie.model.authorization.LoginSharedPref
import com.crashlytics.android.Crashlytics
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase


class UserFragment : Fragment() {
    private lateinit var logOut: Button
    private lateinit var crash: Button
    private lateinit var userName: TextView
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics
        var bundle = Bundle()
        bundle.putString("page","page_profile")
        firebaseAnalytics.logEvent("clicked",bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view: View = LayoutInflater.from(container?.context)
            .inflate(R.layout.fragment_user, container, false)
        userName = view.findViewById(R.id.user_name)
        if (LoginSharedPref().getUserName(activity)?.length == 0) {

        } else {
            userName.text = LoginSharedPref().getUserName(activity)
        }
        logOut = view.findViewById(R.id.logOut)!!
        crash = view.findViewById(R.id.crash)!!
        logOut.setOnClickListener(View.OnClickListener {
            LoginSharedPref().clearUserName(requireActivity())
            val intent = Intent(this.context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

            startActivity(intent)
        })
        crash.setOnClickListener {
            Crashlytics.getInstance().crash()
        }
        return view
    }
}
