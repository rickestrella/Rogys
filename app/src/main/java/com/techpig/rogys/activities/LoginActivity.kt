package com.techpig.rogys.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.techpig.rogys.BaseActivity
import com.techpig.rogys.R
import com.techpig.rogys.firestore.FirestoreClass
import com.techpig.rogys.models.User
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        tv_register.setOnClickListener(this)
        btn_login.setOnClickListener(this)
        tv_forgot_password.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.tv_register -> {
                    val register = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(register)
                }
                R.id.btn_login -> {
                    loginRegisteredUser()
                }
                R.id.tv_forgot_password -> {
                    val forgot = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                    startActivity(forgot)
                }
            }
        }
    }

    private fun loginRegisteredUser() {
        if (validateLoginDetails()) {
            showProgressDialog("Iniciando sesión...")
            val email: String = til_email.editText!!.text.toString().trim { it <= ' ' }
            val password: String =
                til_password.editText!!.text.toString().trim { it <= ' ' }
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        FirestoreClass().getUserDetails(this@LoginActivity)
                    } else {
                        hideProgressDialog()
                        try {
                            throw task.exception!!
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            showAlerter(
                                "Error de autenticación",
                                "Usuario o contraseña no válidos",
                                true,
                                2500
                            )
                            til_password.requestFocus()
                        } catch (e: Exception) {
                            when (e.localizedMessage) {
                                "There is no user record corresponding to this identifier. The user may have been deleted." -> {
                                    showAlerter(
                                        "Error de autenticación",
                                        "El usuario no existe o ha sido borrado",
                                        true,
                                        2500
                                    )
                                }
                                "The user account has been disabled by an administrator." -> {
                                    showAlerter(
                                        "Cuenta inhabilitada",
                                        "Tu cuenta ha sido restringida por no respetar nuestros términos y condiciones.\nSi crees que ha sido un error, por favor, comunícate de inmediato con Ricardo Estrella al 0 979 102 096.",
                                        true,
                                        6000
                                    )
                                }
                                else -> {
                                    Log.e("New exception", e.message!!)
                                }
                            }
                        }
                    }
                }
        }
    }

    private fun validateLoginDetails(): Boolean {
        if (TextUtils.isEmpty(til_email.editText!!.text.toString().trim { it <= ' ' })) {
            showAlerter(
                "Error",
                "Verifica que hayas escrito el correo electrónico",
                true,
                3000
            )
            return false
        } else if (TextUtils.isEmpty(til_password.editText!!.text.toString().trim { it <= ' ' })) {
            showAlerter(
                "Error",
                "¿Te olvidaste de ingresar la contraseña?",
                true,
                3000
            )
            return false
        }
        return true
    }

    fun userLoggedInSuccess(user: User) {
        hideProgressDialog()

        Log.i("First name", user.firstName)
        Log.i("Last name", user.lastName)
        Log.i("e-mail", user.email)

        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }
}