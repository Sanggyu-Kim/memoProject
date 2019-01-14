package com.example.s_kim.memoproject


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import android.view.View
import android.widget.*
import com.google.android.gms.common.ConnectionResult
import com.google.firebase.auth.GoogleAuthProvider







class MainActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    var id: EditText? = null
    var password:EditText? = null
    var pbLogin:ProgressBar? = null
    var Google_Login: SignInButton? = null
    private val RC_SIGN_IN = 1000
    private var mAuth: FirebaseAuth? = null
    private var mGoogleApiClient: GoogleApiClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        id = findViewById(R.id.etId)
        password = findViewById(R.id.etPassword)
        pbLogin= findViewById(R.id.pbLogin)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        mAuth = FirebaseAuth.getInstance()//mAuth는 FirebaseAuth 인스턴스 변수로 선언하여 Firebase 인증을 사용할 수 있게 초기화 해줍니다.

        findViewById<SignInButton>(R.id.Google_Login).setOnClickListener{
            pbLogin?.setOnSystemUiVisibilityChangeListener { View.VISIBLE }

            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
            pbLogin?.setOnSystemUiVisibilityChangeListener { View.GONE }

        }


        findViewById<Button>(R.id.btnRegister).setOnClickListener{
            pbLogin?.setOnSystemUiVisibilityChangeListener { View.VISIBLE }

        }


        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            pbLogin?.setOnSystemUiVisibilityChangeListener { View.VISIBLE }

            val loginIntent = Intent(
                this
                , MemoListActivity::class.java
            )
            startActivity(loginIntent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        pbLogin?.setOnSystemUiVisibilityChangeListener { View.GONE }

        if (requestCode === RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)

            if (result.isSuccess) {
                //구글 로그인 성공해서 파베에 인증
                val account = result.signInAccount
                firebaseAuthWithGoogle(account!!)

                val loginIntent = Intent(
                    this
                    , MemoListActivity::class.java
                )
                startActivity(loginIntent)

            } else {
                //구글 로그인 실패
                Toast.makeText(this,"GoogleLogin 실패!!",Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (!task.isSuccessful) {
                    Toast.makeText(this@MainActivity, "인증 실패", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "구글 로그인 인증 성공", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
      }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = this.mAuth?.currentUser
        //updateUI(currentUser)

    }



}