package com.example.everythingclothes.ui.theme.Screens.Signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.everythingclothes.data.AuthViewModel
import com.example.everythingclothes.navigation.ADD_PRODUCTS_URL
import com.example.everythingclothes.ui.theme.EverythingClothesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavController) {
    val authViewModel = AuthViewModel(navController, LocalContext.current)
    Text(
        modifier = Modifier,
        fontSize = 40.sp,
        fontFamily = FontFamily.Cursive,
        fontWeight = FontWeight.Bold,
        text = "Sign up"
    )
    Column(
        modifier = Modifier
            .padding(bottom = 50.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {


        var name by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Enter your name") },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 30.dp),
            shape = RoundedCornerShape(20.dp),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        var email by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Enter your email") },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 8.dp), // Adjusted top padding
            shape = RoundedCornerShape(20.dp),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        var password by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Enter your password") },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 8.dp), // Adjusted top padding
            shape = RoundedCornerShape(20.dp),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        var UserId by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = UserId,
            onValueChange = { UserId = it },
            label = { Text(text = "Enter your Id") },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 8.dp, bottom = 30.dp), // Adjusted top and bottom padding
            shape = RoundedCornerShape(20.dp),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        val context = LocalContext.current

        Button(
            modifier = Modifier
                .padding(top = 7.dp)
                .fillMaxWidth(0.9f),
            shape = RoundedCornerShape(20.dp),
            onClick = {
                authViewModel.signup(name, email, password)
                Toast.makeText(context, "User successfully added", Toast.LENGTH_SHORT).show()

                if (authViewModel.isUserSignedIn()) {
                    navController.navigate(ADD_PRODUCTS_URL)
                } else {
                    // Handle the case where the user is not signed in after signing up
                }
            }
        ) {
            Text(text = "Sign Up")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    EverythingClothesTheme {
        SignupScreen(rememberNavController())
    }
}
