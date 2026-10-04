package com.example.data.model

enum class EducationLevel(
    val title: String,
    val subtitle: String,
    val badge: String,
    val colorHex: Long
) {
    KINDERGARTEN(
        title = "อนุบาล - ปฐมวัย",
        subtitle = "ภาพ เสียง นิทาน และการสัมผัส",
        badge = "👶 ปฐมวัย",
        colorHex = 0xFFF59E0B
    ),
    PRIMARY(
        title = "ประถมต้น - ปลาย (ป.1 - ป.6)",
        subtitle = "การจำลองสถานการณ์ เกม และแบบฝึกหัด",
        badge = "🎒 ประถม",
        colorHex = 0xFF0D9488
    ),
    SECONDARY(
        title = "มัธยมศึกษา (ม.1 - ม.6)",
        subtitle = "คอนเซปต์เชิงลึก แผนผัง และการเตรียมสอบ",
        badge = "🏛️ มัธยม",
        colorHex = 0xFF4F46E5
    ),
    VOCATIONAL(
        title = "ปวช. / ปวส. / มหาวิทยาลัย",
        subtitle = "ทักษะสายอาชีพ ปฏิบัติจริง และโปรเจกต์",
        badge = "⚙️ อาชีวะ/อุดมศึกษา",
        colorHex = 0xFF7C3AED
    )
}

enum class SubjectCategory(val title: String, val icon: String) {
    SCIENCE("วิทยาศาสตร์", "🔬"),
    MATHEMATICS("คณิตศาสตร์", "📐"),
    LANGUAGES("ภาษา (ไทย/อังกฤษ)", "🗣️"),
    SOCIAL("สังคมศึกษา", "🌏"),
    TECH_VOCATIONAL("ช่าง & คอมพิวเตอร์", "💻")
}

data class FlashcardItem(
    val id: String,
    val thaiWord: String,
    val englishWord: String,
    val phonetic: String,
    val emoji: String,
    val category: String,
    val funFact: String
)

data class StoryCharacter(
    val id: String,
    val name: String,
    val emoji: String,
    val soundLabel: String,
    val animationAction: String
)

data class InteractiveStoryPage(
    val pageNumber: Int,
    val title: String,
    val textThai: String,
    val textEng: String,
    val sceneEmoji: String,
    val characters: List<StoryCharacter>,
    val interactivePrompt: String
)

data class DragDropPair(
    val id: String,
    val label: String,
    val matchKey: String,
    val icon: String,
    val description: String
)

data class QuizQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val subject: SubjectCategory,
    val level: EducationLevel,
    val topicName: String
)

data class LifeCycleStage(
    val step: Int,
    val name: String,
    val emoji: String,
    val description: String,
    val funFact: String
)

data class MindMapNode(
    val id: String,
    val label: String,
    val description: String,
    val parentId: String? = null,
    val colorHex: Long = 0xFF4F46E5
)

data class TechnicalGuideStep(
    val stepNumber: Int,
    val title: String,
    val instructions: String,
    val safetyTip: String? = null,
    val codeSnippet: String? = null,
    val diagramSymbol: String? = null
)

data class TechnicalGuide(
    val id: String,
    val title: String,
    val field: String,
    val toolsNeeded: List<String>,
    val steps: List<TechnicalGuideStep>
)

data class RubricScore(
    val criterion: String,
    val maxScore: Int,
    val givenScore: Int,
    val note: String
)

data class StudentSubmission(
    val id: String,
    val studentName: String,
    val projectTitle: String,
    val submittedAt: String,
    val rubricScores: List<RubricScore>,
    val teacherComment: String,
    val status: String
)

data class LivePlayer(
    val id: String,
    val name: String,
    val avatar: String,
    val score: Int,
    val streak: Int
)

data class CreatorTemplate(
    val id: String,
    val title: String,
    val subject: SubjectCategory,
    val level: EducationLevel,
    val icon: String,
    val description: String
)
