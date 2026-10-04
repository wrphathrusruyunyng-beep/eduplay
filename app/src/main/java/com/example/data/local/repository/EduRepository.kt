package com.example.data.local.repository

import com.example.data.local.dao.EduDao
import com.example.data.local.entities.CreatedMediaEntity
import com.example.data.local.entities.QuizAttemptEntity
import com.example.data.local.entities.UserProfileEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class EduRepository(private val dao: EduDao) {

    val userProfile: Flow<UserProfileEntity?> = dao.getUserProfile()
    val allCreatedMedia: Flow<List<CreatedMediaEntity>> = dao.getAllCreatedMedia()
    val allQuizAttempts: Flow<List<QuizAttemptEntity>> = dao.getAllQuizAttempts()

    suspend fun ensureProfileExists() {
        val current = dao.getUserProfile().firstOrNull()
        if (current == null) {
            dao.insertOrUpdateProfile(UserProfileEntity())
        }
    }

    suspend fun addReward(xpGained: Int, coinsGained: Int) {
        val current = dao.getUserProfile().firstOrNull() ?: UserProfileEntity()
        val newXp = current.xp + xpGained
        val newLevel = (newXp / 150) + 1
        val newCoins = current.coins + coinsGained
        dao.insertOrUpdateProfile(
            current.copy(
                xp = newXp,
                level = newLevel,
                coins = newCoins
            )
        )
    }

    suspend fun saveQuizAttempt(
        topicName: String,
        subject: String,
        educationLevel: String,
        score: Int,
        total: Int
    ) {
        val percentage = if (total > 0) (score * 100) / total else 0
        val isWeak = percentage < 60
        dao.insertQuizAttempt(
            QuizAttemptEntity(
                topicName = topicName,
                subject = subject,
                educationLevel = educationLevel,
                score = score,
                totalQuestions = total,
                percentage = percentage,
                isWeakTopic = isWeak
            )
        )
        // Award XP and coins
        addReward(xpGained = score * 30 + 20, coinsGained = score * 5 + 10)
    }

    suspend fun saveCreatedMedia(
        title: String,
        mediaType: String,
        subject: String,
        educationLevel: String,
        summary: String,
        contentData: String
    ): Long {
        val id = dao.insertCreatedMedia(
            CreatedMediaEntity(
                title = title,
                mediaType = mediaType,
                subject = subject,
                educationLevel = educationLevel,
                summary = summary,
                contentData = contentData
            )
        )
        addReward(xpGained = 100, coinsGained = 25)
        return id
    }

    suspend fun deleteCreatedMedia(id: Long) {
        dao.deleteCreatedMediaById(id)
    }

    suspend fun updateSelectedLevel(levelName: String) {
        val current = dao.getUserProfile().firstOrNull() ?: UserProfileEntity()
        dao.insertOrUpdateProfile(current.copy(selectedLevelName = levelName))
    }
}
