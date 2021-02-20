package com.innodroid.foodscannerapp.ui.account

import alert
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import coil.load
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.innodroid.foodscannerapp.R
import com.innodroid.foodscannerapp.databinding.FragmentAccountBinding
import com.innodroid.foodscannerapp.services.auth.AuthenticationService
import org.koin.android.ext.android.inject


class AccountFragment : Fragment() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private var googleAuth: GoogleSignInClient? = null
    private val auth by inject<AuthenticationService>()
    private var binding: FragmentAccountBinding? = null

    companion object {
        private const val RC_SIGN_IN = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth.user.observe(this) {
            updateUI()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        activity?.let {
            googleAuth = GoogleSignIn.getClient(it, gso);
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false).apply {
            login.setSize(SignInButton.SIZE_WIDE)

            login.setOnClickListener {
                signIn()
            }

            logout.setOnClickListener {
                signOut()
            }
        }

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
    }

    private fun updateUI() {
        val user = auth.user.value
        val binding = binding ?: return

        if (user == null) {
            binding.login.isGone = false
            binding.logout.isGone = true
            binding.profileImage.isGone = true
        } else {
            binding.login.isGone = true
            binding.logout.isGone = false

            if (user.profileImage == null) {
                binding.profileImage.isGone = true
            } else {
                binding.profileImage.isGone = false
                binding.profileImage.load(user.profileImage)
            }
        }
    }

    private fun signIn() {
        googleAuth?.signInIntent?.let {
            startActivityForResult(it, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            try {
                GoogleSignIn.getSignedInAccountFromIntent(data)?.let {
                    handleSignInResult(it)
                }
            } catch (ex: ApiException) {
                ex.printStackTrace()
                alert(ex.message)
            }
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        val account = task.getResult(ApiException::class.java) ?: return
        val idToken = account.idToken ?: return

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
    }

    private fun signOut() {
        firebaseAuth.signOut()
        googleAuth?.signOut()
    }
}
