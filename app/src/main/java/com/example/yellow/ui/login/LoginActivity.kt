package com.example.yellow.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.yellow.Constants.ACCESS_TOKEN
import com.example.yellow.InternetConnectionChecker
import com.example.yellow.R
import com.example.yellow.ui.main.JogsActivity
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_login.loginButton
import kotlinx.android.synthetic.main.activity_login.uuidInputEditText

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel>()

    @Inject
    lateinit var connectionChecker: InternetConnectionChecker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContentView(R.layout.activity_login)

        initViews()
        supportActionBar?.setTitle(R.string.login)

        viewModel.viewCommands.observe(this, { command ->
            when (command) {
                is LoginViewModel.ViewCommand.OnSuccessLogin -> onSuccessLogin(command.token)
                LoginViewModel.ViewCommand.OnFailureLogin -> onFailureLogin()
                LoginViewModel.ViewCommand.NoInternetConnection -> {
                    Toast.makeText(this, R.string.server_error, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun onLoginButtonClick() {
        viewModel.auth(
            connectionChecker.isNetworkAvailable(this),
            uuidInputEditText.text.toString()
        )
    }

    private fun onSuccessLogin(token: String) {
        Prefs.putString(ACCESS_TOKEN, token)
        val intent = Intent(this, JogsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finishAffinity()
    }

    private fun onFailureLogin() {
        Toast.makeText(this, R.string.login_error, Toast.LENGTH_SHORT).show()
    }

    private fun initViews() {
        uuidInputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginButton.isEnabled = s.toString().isNotEmpty()
            }
        })
        loginButton.setOnClickListener {
            onLoginButtonClick()
        }
    }
}
