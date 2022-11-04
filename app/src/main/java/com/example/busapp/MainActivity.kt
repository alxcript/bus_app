package com.example.busapp


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import com.example.busapp.ui.theme.BusAppTheme
import com.google.firebase.auth.FirebaseAuth

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser


        Handler(Looper.getMainLooper()).postDelayed( {
            if(user != null){
                val accountActivity = Intent(this, BusMapActivity::class.java)
                startActivity(accountActivity)
                finish()
            }else{
                val loginActivity = Intent(this, LoginActivity::class.java)
                startActivity(loginActivity)
                finish()
            }
        }, 2000)

        setContent {
            BusAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Text(text = "Verificando..")
                }
            }
        }

    }
}
