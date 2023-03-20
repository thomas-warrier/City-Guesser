package com.example.projetfinalekotlin.connection

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.projetfinalekotlin.country.CountryActivity
import com.example.projetfinalekotlin.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginActivity : AppCompatActivity() {
    private lateinit var googleButton: ImageButton
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        googleButton = binding.googleSignInButton

        firebaseAuth = FirebaseAuth.getInstance()




        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("576622221785-chp84c9822q6rlp2a7n3i7regdqi9bf6")
            .requestEmail()
            .build()




        googleSignInClient = GoogleSignIn.getClient(this, options)


        val someActivityResultLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                Log.d("result", result.toString())
                Log.d("data", result.data.toString())
                Log.d("intent extra", result.data?.extras.toString())
                Log.d("logsign", result.resultCode.toString())


                if (result.resultCode == Activity.RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    val account = task.getResult(ApiException::class.java)
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    firebaseAuth.signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val intentSign = Intent(this, CountryActivity::class.java)
                                startActivity(intentSign)
                                finish()
                            } else {
                                Toast.makeText(
                                    this,
                                    task.exception?.message ?: "Erreur de connexion",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        }
                }

            }
        googleButton.setOnClickListener {
            val intent = googleSignInClient.signInIntent
            someActivityResultLauncher.launch(intent)

        }
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) {
            val intentSign = Intent(this, CountryActivity::class.java)
            startActivity(intentSign)
            finish()
        }
    }

}
