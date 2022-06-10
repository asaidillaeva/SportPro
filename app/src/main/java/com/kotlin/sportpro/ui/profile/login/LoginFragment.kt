package com.kotlin.sportpro.ui.profile.login

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.UserData
import com.kotlin.sportpro.data.model.login.LoginRequestBody
import com.kotlin.sportpro.data.model.login.UserLogin
import com.kotlin.sportpro.ui.profile.UserViewModel
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.error_page.*
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.concurrent.TimeUnit


class LoginFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels {
        InjectorObject.getUserViewModelFactory()
    }
    val TAG = LoginFragment::class.java.simpleName

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var verificationId: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var phoneNumber: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login_page.visibility = View.VISIBLE
        error_page.visibility = View.GONE
        code_page.visibility = View.GONE
        progress_bar.visibility = View.GONE


        val callbacks = object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(authCredential: PhoneAuthCredential) {
                signInToFirebase(authCredential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                showError(e.message)
            }

            override fun onCodeSent(Id: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(Id, token)
                verificationId = Id
                resendToken = token
                showCodeView()
            }
        }

        btnBackFromLogin.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToProfileFragment()
            findNavController().navigate(action)
        }

        btnLogin.setOnClickListener {
            phoneNumber = editTextNumber.text.toString().trim()
            Log.e(LoginFragment::class.java.simpleName, phoneNumber)

            if (isValidPhoneNumber(phoneNumber)) {
                progress_bar.visibility = View.VISIBLE
                login_page.visibility = View.GONE
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,
                    60L,
                    TimeUnit.SECONDS,
                    requireActivity(),
                    callbacks
                )
            } else {
                editTextNumber.error = "Type valid phone number"
            }
        }

        btnConfirmCode.setOnClickListener {
            val code: String = codeEditText.text.trim().toString()
            val credential = PhoneAuthProvider.getCredential(verificationId, code)
            signInToFirebase(credential)
        }

        btnSendAgain.setOnClickListener{

        }
    }

    private fun signInToFirebase(phoneAuthCredentials: PhoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredentials)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {

                    // Sign in success, update UI with the signed-in user's information
                    val token = task.result?.user?.getIdToken(true).toString()
                    Log.e(LoginFragment::class.java.simpleName, token)

                    //Send phone to Api and get Token
                    sendToken(phoneNumber)
                } else {
                    showError(task.exception?.message)
                }
            }
    }

    //Sends phoneNumber and token from firebase to Backend and receives Token which will be used in future
    private fun sendToken(phoneNumber: String) {
        val userLogin = UserLogin("+" + phoneNumber.filter { it.isDigit() })
        viewModel.login(LoginRequestBody( userLogin))
            .observe(viewLifecycleOwner) {
                when (it) {
                    is ApiResult.Success -> {
                        progress_bar.visibility = View.GONE
                        UserData.of(requireContext()).saveToken(it.data.user.token)
                        Thread {
                            Thread.sleep(500)
                            activity?.supportFragmentManager?.popBackStack()
                        }.start()
                    }
                    is ApiResult.Error -> {
                        if (it.throwable.message.toString().contains("401")) {
                            showError("Вы не зарегистрированы. Обратитесь к представителю федерации для регистрации.")
                            return@observe
                        } else {
                            showError(it.throwable.message.toString())
                        }
                        val user = mAuth.currentUser!!

                        user.delete()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.e( "$TAG : line 143", "User account deleted.")
                                }
                            }

                    }
                    is ApiResult.Loading -> {
                        progress_bar.visibility = View.VISIBLE
                    }
                }
            }
    }

    private fun showError(message: String?) {
        login_page.visibility = View.GONE
        error_page.visibility = View.VISIBLE
        code_page.visibility = View.GONE
        progress_bar.visibility = View.GONE
        errorMsgTv.text = message.toString()
    }

    private fun showCodeView() {

        textCode.text = "На номер $phoneNumber отправлен код подтверждения. "
        login_page.visibility = View.GONE
        error_page.visibility = View.GONE
        code_page.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
    }

    private fun isValidPhoneNumber(phone: String): Boolean {
        if (phone != "" && phone.length > 18)
            return Patterns.PHONE.matcher(phone).matches()

        return false
    }

}


