package com.hasancanbuyukasik.cryptotracker.data.remote.helpers

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.hasancanbuyukasik.cryptotracker.common.FirebaseConstants
import javax.inject.Inject

class FirebaseHelper @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) {

    fun signInFirebase(): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(
            FirebaseConstants.FIREBASE_AUTH_USERNAME,
            FirebaseConstants.FIREBASE_AUTH_PASSWORD
        )
    }

    fun favoriteCoinsCollection(): CollectionReference {
        return firebaseFirestore.collection("users").document(FirebaseConstants.FIRESTROE_DOCUMENT_ID)
            .collection("favoriteCoins")
    }
}