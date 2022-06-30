package com.example.abetterhusbandv2.repository

import com.example.abetterhusbandv2.model.HusbandTask
import com.google.firebase.firestore.CollectionReference
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

    suspend fun getHusbandTaskList() : List<HusbandTask> {
        try {
            return husbandTaskList.get().await().map { it.toObject(HusbandTask::class.java) }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return emptyList()
    }

    fun changeHusbandTaskStatus(husbandTask: HusbandTask) {
        husbandTaskList.document(husbandTask.title).update("done", husbandTask.done)
    }
}