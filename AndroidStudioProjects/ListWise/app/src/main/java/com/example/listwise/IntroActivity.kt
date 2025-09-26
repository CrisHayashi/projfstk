package com.example.listwise

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class IntroActivity : AppCompatActivity() {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        // Pega a referência do logo
        val logo: ImageView = findViewById(R.id.logoImage)
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in_scale)

        // Aplica a animação no logo
        logo.startAnimation(animation)

        // Listener para detectar quando a animação terminar
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(anim: Animation?) {
                // nada aqui
            }

            override fun onAnimationRepeat(anim: Animation?) {
                // Nada a fazer aqui
            }

            override fun onAnimationEnd(anim: Animation?) {
                // espera 1s depois da animação (opcional)
                Handler(Looper.getMainLooper()).postDelayed({
                    routeAfterIntro()
                }, 1000)
            }
        })
    }

    private fun routeAfterIntro() {
        val current = auth.currentUser
        val intent = if (current != null) {
            // Usuário já logado -> vai direto para MainActivity
            Intent(this, MainActivity::class.java)
        } else {
            // Usuário não logado -> fluxo de autenticação
            Intent(this, AuthActivity::class.java)
        }

        // Evita voltar para a intro ao apertar "voltar"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}