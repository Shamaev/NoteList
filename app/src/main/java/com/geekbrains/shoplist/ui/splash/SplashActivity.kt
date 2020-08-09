package com.geekbrains.shoplist.ui.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.geekbrains.shoplist.R
import com.geekbrains.shoplist.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    private lateinit var viewModel: SplashViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        auth = FirebaseAuth.getInstance()

        singOnClick()
        registerOnClick()
    }

    private fun singOnClick() {
        sing.setOnClickListener {
            signIn(email.text.toString(), pass.text.toString())
        }
    }

    private fun registerOnClick() {
        registration.setOnClickListener {
            createAccount(email.text.toString(), pass.text.toString())
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser

                    viewModel.requestUser(email)
                    startActivity()

                    Toast.makeText(baseContext, getString(R.string.completeRegistration),
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, getString(R.string.loseRegistration),
                        Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser

                    viewModel.requestUser(email)
                    startActivity()

                    Toast.makeText(baseContext, getString(R.string.completeSingIn),
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, getString(R.string.loseSingIn),
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun startActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
