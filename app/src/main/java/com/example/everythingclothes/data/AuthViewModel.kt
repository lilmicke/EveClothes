package com.example.everythingclothes.data

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.navigation.NavController
import com.example.everythingclothes.models.Users
import com.example.everythingclothes.navigation.HOME_URL
import com.example.everythingclothes.navigation.LOGIN_URL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthViewModel(var navController: NavController, var context: Context) {
    private val mAuth: FirebaseAuth
    private val progress: ProgressDialog
    private val sharedPreferences: SharedPreferences

    init {
        mAuth = FirebaseAuth.getInstance()
        progress = ProgressDialog(context)
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")
        sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    }

    fun signup(name: String, email: String, password: String) {
        progress.show()
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            var userId = mAuth.currentUser!!.uid
            var userProfile = Users(name, email, password, userId)
            // Create a reference table called Users inside of the Firebase database
            var usersRef = FirebaseDatabase.getInstance().getReference()
                .child("Users/$userId")
            usersRef.setValue(userProfile).addOnCompleteListener {
                progress.dismiss()
                if (it.isSuccessful) {
                    saveUserLoginStatus(true)
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    navController.navigate(LOGIN_URL)
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun isUserSignedIn(): Boolean {
        return mAuth.currentUser != null
    }

    fun login(email: String, password: String) {
        progress.show()
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            progress.dismiss()
            if (it.isSuccessful) {
                saveUserLoginStatus(true)
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                navController.navigate(HOME_URL)
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun logout() {
        mAuth.signOut()
        saveUserLoginStatus(false)
        navController.navigate(LOGIN_URL)
    }

    fun isLoggedIn(): Boolean = getUserLoginStatus()

    private fun saveUserLoginStatus(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("isLoggedIn", isLoggedIn).apply()
    }

    private fun getUserLoginStatus(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }
}