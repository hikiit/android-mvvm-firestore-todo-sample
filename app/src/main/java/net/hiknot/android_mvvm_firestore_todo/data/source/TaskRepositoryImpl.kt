package net.hiknot.android_mvvm_firestore_todo.data.source

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import net.hiknot.android_mvvm_firestore_todo.data.Task
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val firebaseProfile: FirebaseProfileService,
    private val firestore: FirebaseFirestore,
) : TaskRepository {

    companion object {
        private const val TAG = "TaskRepository"
    }

    private val tasksCollectionPath = firestore
        .collection("users")
        .document(firebaseProfile.uid!!)
        .collection("tasks")

    override fun addTask(task: Task) {
        tasksCollectionPath.document().set(task)
    }

    override fun updateTaskStatus(task: Task) {
        task.documentId?.let {
            // FIXME fieldの指定はハードコーディングではなく、data classの変数名を利用できるようにしたい
            tasksCollectionPath.document(it)
                .update("isDone", task.isDone, "updateAt", FieldValue.serverTimestamp())
        }
    }

    override fun removeTask(documentId: String) {
        tasksCollectionPath.document(documentId).delete()
    }

    override suspend fun getTask(documentId: String): Task? {
        return tasksCollectionPath.document(documentId).get().await().toObject(Task::class.java)
    }

    override fun getTasks(): Flow<List<Task>> = callbackFlow {

        val register = tasksCollectionPath.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e(TAG, "Listen failed.", error)
                close()
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.documents.isNotEmpty()) {
                val tasks = snapshot.toObjects(Task::class.java)
                Log.d(TAG, "TaskStream: $tasks")
                trySend(tasks)
            } else {
                Log.d(TAG, "TaskStream is null")
                trySend(listOf())
            }
        }

        awaitClose { register.remove() }
    }
}
