package com.example.abetterhusbandv2.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.abetterhusbandv2.model.HusbandTask
import com.example.abetterhusbandv2.model.TaskList
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.toObject
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class HusbandTaskRepository @Inject constructor(
    @Named("HusbandTaskList") private val husbandTaskList: CollectionReference
) {

    fun addOrUpdateHusbandTask(listId: String, husbandTask: HusbandTask) {
        husbandTaskList.document(listId).collection("list").document(husbandTask.taskId)
            .set(husbandTask)
    }

    fun removeHusbandTask(listId: String, husbandTask: HusbandTask) {
        husbandTaskList.document(listId).collection("list").document(husbandTask.taskId).delete()
    }

    private var getHusbandTaskListSuccessListener: ((List<HusbandTask>) -> Unit)? = null

    fun getHusbandTaskListSuccessListener(success: (List<HusbandTask>) -> Unit) {
        getHusbandTaskListSuccessListener = success
    }

    fun getHusbandTaskListById(listId: String) {
        husbandTaskList.document(listId).collection("list").addSnapshotListener { value, e ->
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

    fun updateTaskListHusband(listId: String, husband: String) {
        husbandTaskList.document(listId).update("husband", husband)
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

}