package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val displayName: String = "ผู้เรียนคนเก่ง (EduLearner)",
    val xp: Int = 350,
    val level: Int = 3,
    val streakDays: Int = 5,
    val coins: Int = 120,
    val badgesUnlocked: String = "first_quiz,streak_3,creator_pioneer,sound_explorer",
    val selectedLevelName: String = "PRIMARY"
)
