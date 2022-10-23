package net.hiknot.android_mvvm_firestore_todo.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Task(
    @DocumentId val documentId: String? = null,
    val title: String = "",
    @field:JvmField // use this annotation if your Boolean field is prefixed with 'is'
    val isDone: Boolean = false,
    @ServerTimestamp val createAt: Date? = null,
    @ServerTimestamp val updateAt: Date? = null,
)
