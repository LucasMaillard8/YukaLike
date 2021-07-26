package com.yukaprojetandroidesgi.yukalike.business.service

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yukaprojetandroidesgi.yukalike.business.model.User

object FirestoreProvider {
    private lateinit var database: FirebaseFirestore

    private fun setDatabase() {
        database = Firebase.firestore
    }

    fun getUserPoint(listener: NetworkListener<Long>) {
        setDatabase()
        val user = Firebase.auth.currentUser
        user?.let {
            database.collection("scores").document(it.uid).get().addOnSuccessListener { doc ->
                Log.d("LV", "success of get user data")
                doc.get("point").let { data ->
                    val point = data as Long
                    listener.onSuccess(point)
                }
            }.addOnFailureListener {
                Log.d("LV", "error of get user data")
                listener.onError(500)
            }
        } ?: return listener.onError(401)
    }

    fun addPointToUser(pointToAdd: Long) {
        setDatabase()
        val user = Firebase.auth.currentUser
        user?.let {
            // get user document
            database.collection("scores").document(user.uid).get().addOnSuccessListener {
                Log.d("LV", "success of get user data")
                if(it.exists()) {
                    val user = it.toObject(User::class.java)
                    user?.point?.let {
                        val point = it + pointToAdd
                        val pointHashMap = hashMapOf( "point" to point )
                        addPointToUserInDb(pointHashMap)
                    } ?: createUserAndStorePoint(pointToAdd)
                } else createUserAndStorePoint(pointToAdd)
            }.addOnFailureListener {
                Log.d("LV", "error of get user data")
            }
            // create user document if null
            // database.collection("scores").document(user.uid)
        }

    }

    private fun createUserAndStorePoint(point: Long) {
        val pointHashMap = hashMapOf( "point" to point )
        addPointToUserInDb(pointHashMap)
    }

    private fun addPointToUserInDb(pointHashMap: HashMap<String,Long>) {
        val user = Firebase.auth.currentUser
        user?.let {
            database.collection("scores").document(it.uid).set(pointHashMap).addOnSuccessListener {
                Log.d("LV", "success of rewrite data")
            }.addOnFailureListener {
                Log.d("LV", "error of rewrite data")
            }
        }
    }
}