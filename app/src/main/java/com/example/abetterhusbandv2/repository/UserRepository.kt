package com.example.abetterhusbandv2.repository

import com.example.abetterhusbandv2.model.User
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.toObject
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    @Named("Users") private val usersCollection: CollectionReference
) {
    fun addOrUpdateUser(user: User) {
        usersCollection.document(user.userId).set(user)
    }

    fun getListIdByUserId(userId: String): String {
        var listId = ""
        usersCollection.document(userId).get().addOnSuccessListener { doc ->
            if (doc != null) {
                listId = doc.data?.get("listId") as String
            }
        }

        return listId
    }

    private var getUserSuccess: ((User) -> Unit)? = null

    fun getUserSuccessListener(success: (User) -> Unit) {
        getUserSuccess = success
    }

    fun getUserById(userId: String) {
        usersCollection.document(userId).get().addOnSuccessListener { doc ->
            if (doc != null) {
                val user = doc.toObject<User>()

                getUserSuccess?.invoke(user!!)
            }
        }
        /*usersCollection.document(userId).addSnapshotListener { doc, e ->
            if (e != null) {
                Log.w("Debug", "listen:error", e)
                return@addSnapshotListener
            }

            if (doc != null) {
                val user = doc.toObject<User>()

                getUserSuccess?.invoke(user!!)
            }
        }*/
    }
}