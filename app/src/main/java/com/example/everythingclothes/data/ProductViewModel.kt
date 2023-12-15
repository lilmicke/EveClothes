package com.example.everythingclothes.data

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import com.example.everythingclothes.models.Product
import com.example.everythingclothes.navigation.LOGIN_URL
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class ProductViewModel(var navController:NavHostController, var context: Context) {
    var authViewModel:AuthViewModel
    var progress:ProgressDialog
    init {
        authViewModel = AuthViewModel(navController, context)
        if (!authViewModel.isLoggedIn()){
            navController.navigate(LOGIN_URL)
        }
        progress = ProgressDialog(context)
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")
    }

    fun uploadProduct(name: String, quantity: String, price: String, filePath: Uri) {
        val productId = System.currentTimeMillis().toString()
        val storageRef = FirebaseStorage.getInstance().getReference("Products").child(productId)

        progress.show()

        storageRef.putFile(filePath).addOnCompleteListener { uploadTask ->
            progress.dismiss()

            if (uploadTask.isSuccessful) {
                // File uploaded successfully, now get the download URL
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    val product = Product(name, quantity, price, imageUrl, productId)

                    // Save data to the database
                    val databaseRef = FirebaseDatabase.getInstance().getReference("Products").child(productId)
                    databaseRef.setValue(product).addOnCompleteListener { databaseTask ->
                        if (databaseTask.isSuccessful) {
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Error: ${databaseTask.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(context, "Error getting download URL: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Upload error: ${uploadTask.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun allProducts(
        product:MutableState<Product>,
        products:SnapshotStateList<Product>):SnapshotStateList<Product>{
        progress.show()
        var ref = FirebaseDatabase.getInstance().getReference()
            .child("Products")
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                products.clear()
                for (snap in snapshot.children){
                    var retrievedProduct = snap.getValue(Product::class.java)
                    product.value = retrievedProduct!!
                    products.add(retrievedProduct)
                }
                progress.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "DB locked", Toast.LENGTH_SHORT).show()
            }
        })
        return products
    }

    fun getAllProducts(): List<Product> {
        val productsList = mutableListOf<Product>()

        FirebaseDatabase.getInstance().getReference("Products")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (productSnapshot in snapshot.children) {
                        val product = productSnapshot.getValue(Product::class.java)
                        product?.let { productsList.add(it) }
                    }
                    Log.d("ProductViewModel", "Products fetched successfully: $productsList")
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("ProductViewModel", "Error fetching products: $error")
                }
            })

        return productsList
    }

    fun deleteProduct(productId:String){
        var ref = FirebaseDatabase.getInstance().getReference()
            .child("Products/$productId")
        ref.removeValue()
        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    }
}
