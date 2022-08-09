package com.example.abetterhusbandv2.repository

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.example.abetterhusbandv2.model.HusbandTask
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HusbandTaskRepository @Inject constructor(
    private val husbandTaskList: CollectionReference
) {

    fun addHusbandTask(husbandTask: HusbandTask) {
        husbandTaskList.document(husbandTask.title).set(husbandTask)
    }

    fun removeHusbandTask(husbandTask: HusbandTask) {
        husbandTaskList.document(husbandTask.title).delete()
    }

    private var getHusbandTaskListSuccessListener: ((List<HusbandTask>) -> Unit)? = null

    fun getHusbandTaskListSuccessListener(success: (List<HusbandTask>) -> Unit) {
        getHusbandTaskListSuccessListener = success
    }

    fun getHusbandTaskList() {
        husbandTaskList.addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "listen:error", e)
                return@addSnapshotListener
            }

            if (value != null) {
                val list = mutableListOf<HusbandTask>()

                value.forEach { doc ->
                    list.add(doc.toObject())
                }

                getHusbandTaskListSuccessListener?.invoke(list)
            }
        }
    }

    // Another method for realtime updates
    /*fun getHusbandTaskList(): Flow<List<HusbandTask>> {
        return callbackFlow {
            val listener = husbandTaskList.addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(TAG, "listen:error", e)
                    return@addSnapshotListener
                }

                if (value != null) {
                    val list = mutableListOf<HusbandTask>()

                    value.forEach { doc ->
                        list.add(doc.toObject())
                    }

                    val map = value.documents.mapNotNull { it.toObject<HusbandTask>() }

                    trySend(map)
                }
            }

            awaitClose {
                Log.d(TAG, "Cancelling tasks listener")
                listener.remove()
            }
        }
    }*/

    fun changeHusbandTaskStatus(husbandTask: HusbandTask) {
        husbandTaskList.document(husbandTask.title).update("done", husbandTask.done)
    }
}