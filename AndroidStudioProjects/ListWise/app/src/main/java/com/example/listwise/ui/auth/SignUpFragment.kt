package com.example.listwise.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.listwise.MainActivity
import com.example.listwise.R
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpFragment : Fragment() {

    private lateinit var inputFirstName: EditText
    private lateinit var inputLastName: EditText
    private lateinit var inputAge: EditText
    private lateinit var inputGender: MaterialAutoCompleteTextView
    private lateinit var inputProfession: EditText
    private lateinit var inputPhone: EditText
    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var inputConfirmPassword: EditText
    private lateinit var btnSignUp: Button

    private lateinit var auth: FirebaseAuth
    private val dbRef = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        // Inicializa Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Inicializar componentes
        inputFirstName = view.findViewById(R.id.inputFirstName)
        inputLastName = view.findViewById(R.id.inputLastName)
        inputAge = view.findViewById(R.id.inputAge)
        inputGender = view.findViewById(R.id.spinnerGender)
        inputProfession = view.findViewById(R.id.inputProfession)
        inputPhone = view.findViewById(R.id.inputPhone)
        inputEmail = view.findViewById(R.id.inputEmail)
        inputPassword = view.findViewById(R.id.inputPassword)
        inputConfirmPassword = view.findViewById(R.id.inputConfirmPassword)
        btnSignUp = view.findViewById(R.id.btnSignUp)

        /// Configurar opções de gênero (AutoComplete)
        val genderOptions = resources.getStringArray(R.array.gender_options)
        val genderAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, genderOptions)
        inputGender.setAdapter(genderAdapter)


        // Máscara do telefone
        setupPhoneMask()

        // Listener do botão
        btnSignUp.setOnClickListener { signUpUser() }

        return view
    }

    private fun setupPhoneMask() {
        inputPhone.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false
            private val mask = "(##) #####-####"

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isUpdating) {
                    isUpdating = false
                    return
                }

                var str = s.toString().filter { it.isDigit() } // apenas números
                var formatted = ""
                var i = 0

                for (m in mask.toCharArray()) {
                    if (m != '#' && str.length > i) {
                        formatted += m
                    } else {
                        try {
                            formatted += str[i]
                            i++
                        } catch (e: Exception) {
                            break
                        }
                    }
                }

                isUpdating = true
                inputPhone.setText(formatted)
                inputPhone.setSelection(formatted.length)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun signUpUser() {
        val firstName = inputFirstName.text.toString().trim()
        val lastName = inputLastName.text.toString().trim()
        val age = inputAge.text.toString().trim()
        val gender = inputGender.text.toString().trim()
        val profession = inputProfession.text.toString().trim()
        val phone = inputPhone.text.toString().trim()
        val email = inputEmail.text.toString().trim()
        val password = inputPassword.text.toString().trim()
        val confirmPassword = inputConfirmPassword.text.toString().trim()

        // Validações
        if (firstName.isEmpty()) {
            inputFirstName.error = "Digite o primeiro nome"
            return
        }
        if (lastName.isEmpty()) {
            inputLastName.error = "Digite o sobrenome"
            return
        }
        if (age.isEmpty() || age.toIntOrNull() == null || age.toInt() <= 0) {
            inputAge.error = "Digite uma idade válida"
            return
        }
        if (gender.isEmpty()) {
            inputGender.error = "Selecione o sexo"
            return
        }
        if (profession.isEmpty()) {
            inputProfession.error = "Digite sua profissão"
            return
        }
        if (phone.isEmpty() || phone.filter { it.isDigit() }.length < 10) {
            inputPhone.error = "Digite um telefone válido"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.error = "Digite um e-mail válido"
            return
        }
        if (password.length < 6) {
            inputPassword.error = "A senha deve ter pelo menos 6 caracteres"
            return
        }
        if (password != confirmPassword) {
            inputConfirmPassword.error = "As senhas não coincidem"
            return
        }

        // Remove máscara para salvar apenas os números
        val rawPhone = phone.filter { it.isDigit() }

        // Cria usuário no FirebaseAuth
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

                val userMap = mapOf(
                    "firstName" to firstName,
                    "lastName" to lastName,
                    "age" to age,
                    "gender" to gender,
                    "profession" to profession,
                    "phone" to rawPhone,
                    "email" to email
                )

                // Salva no Realtime Database
                dbRef.child("users").child(userId).setValue(userMap)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                        // 🔹 Redireciona para MainActivity e finaliza AuthActivity
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                        activity?.finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Erro ao salvar dados: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(requireContext(), "Erro: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}