package com.example.busapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.busapp.ui.theme.BusAppTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : ComponentActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        mAuth = FirebaseAuth.getInstance()

        setContent {
            BusAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Button(onClick = { signIn() }) {
                        Text(text = "Iniciar Sesion")
                    }
                }
            }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent

        resultLauncher.launch(signInIntent)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
        val data: Intent? = result.data
        if(result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception

            if (task.isSuccessful) {
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    if(validUptAccount(account.email!!)) {
                        Log.d("BusAppGSIGN", "firebaseWithGoogle:" + account.id)
                        firebaseAuthWithGoogle(account.idToken!!)
                    } else {
                        googleSignInClient.signOut()
                        Toast.makeText(this, "Debes loguearte con tu correo institucional example@virual.upt.pe", Toast.LENGTH_LONG).show()
                    }

                } catch (e: ApiException) {
                    Log.w("BusAppGSIGN", "Autenticación sin éxito", e)
                }
            } else {
                Log.w("BusAppGSIGN", exception.toString())
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    private fun firebaseAuthWithGoogle(idToken:String){
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        mAuth.signInWithCredential(credential).addOnCompleteListener(this ){
            if(it.isSuccessful){
                Log.d("BusAppGSIGN","credenciales aceptadas")
                val intent = Intent(this,BusMapActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Log.w("BusAppGSIGN","credenciales rechazadas",it.exception)
            }
        }
    }

    private fun validUptAccount(email: String): Boolean {
        return email.split("@")[1] == "virtual.upt.pe"
    }
}