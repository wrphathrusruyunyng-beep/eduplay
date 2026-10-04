package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_attempts")
data class QuizAttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val topicName: String,
    val subject: String,
    val educationLevel: String,
    val score: Int,
    val totalQuestions: Int,
    val percentage: Int,
    val isWeakTopic: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)
