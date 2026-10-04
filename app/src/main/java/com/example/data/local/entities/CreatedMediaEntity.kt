package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "created_media")
data class CreatedMediaEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val mediaType: String, // QUIZ, FLASHCARD, SLIDE_OUTLINE, TECHNICAL_GUIDE, LAB_SIMULATION
    val subject: String,
    val educationLevel: String,
    val summary: String,
    val contentData: String,
    val createdAt: Long = System.currentTimeMillis()
)
