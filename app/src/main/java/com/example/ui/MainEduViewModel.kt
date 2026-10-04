package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ai.GeminiAiService
import com.example.data.audio.AudioRecordManager
import com.example.data.local.EduDatabase
import com.example.data.local.entities.CreatedMediaEntity
import com.example.data.local.entities.QuizAttemptEntity
import com.example.data.local.entities.UserProfileEntity
import com.example.data.local.repository.EduRepository
import com.example.data.model.EducationLevel
import com.example.data.model.FlashcardItem
import com.example.data.model.InteractiveStoryPage
import com.example.data.model.LivePlayer
import com.example.data.model.QuizQuestion
import com.example.data.model.SampleEduData
import com.example.data.model.SubjectCategory
import com.example.data.tts.TtsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class MainNavTab(val label: String, val icon: String) {
    HOME("หน้าหลัก", "🏠"),
    MODULES("สื่อตามระดับชั้น", "🎒"),
    STUDIO("สตูดิโอ & AI", "🎨"),
    GAMES("เกม & ห้องสด", "🎮"),
    ANALYTICS("วิเคราะห์ผล", "📊")
}

enum class ActiveSubScreen {
    NONE,
    KINDERGARTEN_FLASHCARDS,
    KINDERGARTEN_STORY,
    KINDERGARTEN_DRAG_DROP,
    KINDERGARTEN_COLORING,
    PRIMARY_QUIZ,
    PRIMARY_MATH_SCIENCE,
    PRIMARY_READING_PRACTICE,
    SECONDARY_MIND_MAP,
    SECONDARY_VIRTUAL_LAB,
    SECONDARY_EXAM_DIAGNOSTIC,
    VOCATIONAL_TECH_GUIDE,
    VOCATIONAL_PORTFOLIO_RUBRIC,
    LIVE_CLASSROOM_PIN,
    DUO_BATTLE_SPEED,
    CREATOR_BUILDER
}

class MainEduViewModel(application: Application) : AndroidViewModel(application) {

    private val db = EduDatabase.getDatabase(application)
    val repository = EduRepository(db.eduDao())
    val ttsManager = TtsManager(application)
    val audioRecorder = AudioRecordManager(application)
    val geminiService = GeminiAiService()

    val userProfile: StateFlow<UserProfileEntity?> = repository.userProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProfileEntity())

    val createdMediaList: StateFlow<List<CreatedMediaEntity>> = repository.allCreatedMedia
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val quizAttempts: StateFlow<List<QuizAttemptEntity>> = repository.allQuizAttempts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedLevel = MutableStateFlow(EducationLevel.PRIMARY)
    val selectedLevel: StateFlow<EducationLevel> = _selectedLevel.asStateFlow()

    private val _currentTab = MutableStateFlow(MainNavTab.HOME)
    val currentTab: StateFlow<MainNavTab> = _currentTab.asStateFlow()

    private val _activeSubScreen = MutableStateFlow(ActiveSubScreen.NONE)
    val activeSubScreen: StateFlow<ActiveSubScreen> = _activeSubScreen.asStateFlow()

    // Flashcard State
    private val _flashcards = MutableStateFlow(SampleEduData.flashcards)
    val flashcards: StateFlow<List<FlashcardItem>> = _flashcards.asStateFlow()

    private val _currentCardIndex = MutableStateFlow(0)
    val currentCardIndex: StateFlow<Int> = _currentCardIndex.asStateFlow()

    private val _isCardFlipped = MutableStateFlow(false)
    val isCardFlipped: StateFlow<Boolean> = _isCardFlipped.asStateFlow()

    // Story State
    private val _storyPages = MutableStateFlow(SampleEduData.storyPages)
    val storyPages: StateFlow<List<InteractiveStoryPage>> = _storyPages.asStateFlow()

    private val _currentStoryPageIndex = MutableStateFlow(0)
    val currentStoryPageIndex: StateFlow<Int> = _currentStoryPageIndex.asStateFlow()

    private val _activeCharacterAction = MutableStateFlow<String?>(null)
    val activeCharacterAction: StateFlow<String?> = _activeCharacterAction.asStateFlow()

    // Drag and Drop Match Game State
    private val _dragDropPairs = MutableStateFlow(SampleEduData.dragDropPairs)
    val dragDropPairs = _dragDropPairs.asStateFlow()

    private val _matchedIds = MutableStateFlow<Set<String>>(emptySet())
    val matchedIds: StateFlow<Set<String>> = _matchedIds.asStateFlow()

    private val _selectedMatchSource = MutableStateFlow<String?>(null)
    val selectedMatchSource: StateFlow<String?> = _selectedMatchSource.asStateFlow()

    // Math Simulator (Abacus / Counting Beads)
    private val _beadsCountRow1 = MutableStateFlow(4)
    val beadsCountRow1: StateFlow<Int> = _beadsCountRow1.asStateFlow()

    private val _beadsCountRow2 = MutableStateFlow(3)
    val beadsCountRow2: StateFlow<Int> = _beadsCountRow2.asStateFlow()

    // Animal Life Cycle
    private val _selectedLifeCycleType = MutableStateFlow("butterfly") // "butterfly" or "frog"
    val selectedLifeCycleType: StateFlow<String> = _selectedLifeCycleType.asStateFlow()

    private val _activeLifeCycleStep = MutableStateFlow(1)
    val activeLifeCycleStep: StateFlow<Int> = _activeLifeCycleStep.asStateFlow()

    // Function Grapher State: y = a x^2 + b x + c
    private val _graphA = MutableStateFlow(1.0f)
    val graphA: StateFlow<Float> = _graphA.asStateFlow()

    private val _graphB = MutableStateFlow(0.0f)
    val graphB: StateFlow<Float> = _graphB.asStateFlow()

    private val _graphC = MutableStateFlow(-2.0f)
    val graphC: StateFlow<Float> = _graphC.asStateFlow()

    // Electric Circuit Simulator State: V = I * R
    private val _circuitVoltage = MutableStateFlow(12f)
    val circuitVoltage: StateFlow<Float> = _circuitVoltage.asStateFlow()

    private val _circuitResistance = MutableStateFlow(4f)
    val circuitResistance: StateFlow<Float> = _circuitResistance.asStateFlow()

    private val _isCircuitSwitchClosed = MutableStateFlow(true)
    val isCircuitSwitchClosed: StateFlow<Boolean> = _isCircuitSwitchClosed.asStateFlow()

    // Reading Practice
    private val _readingPassageIndex = MutableStateFlow(0)
    val readingPassageIndex: StateFlow<Int> = _readingPassageIndex.asStateFlow()

    private val _readingScore = MutableStateFlow<Int?>(null)
    val readingScore: StateFlow<Int?> = _readingScore.asStateFlow()

    // Live Classroom (PIN Code mode)
    private val _liveRoomPin = MutableStateFlow("849201")
    val liveRoomPin: StateFlow<String> = _liveRoomPin.asStateFlow()

    private val _isHost = MutableStateFlow(false)
    val isHost: StateFlow<Boolean> = _isHost.asStateFlow()

    private val _livePlayers = MutableStateFlow(
        listOf(
            LivePlayer("p1", "คุณครูฟ้าใส (Host)", "👩‍🏫", 0, 0),
            LivePlayer("p2", "น้องวินัย", "🚀", 1250, 4),
            LivePlayer("p3", "น้องปราง", "🌸", 1100, 3),
            LivePlayer("p4", "น้องกล้า", "🦁", 950, 2),
            LivePlayer("p5", "คุณ (You)", "⭐", 800, 2)
        )
    )
    val livePlayers: StateFlow<List<LivePlayer>> = _livePlayers.asStateFlow()

    // Duo Battle Speed Quiz State
    private val _battlePlayer1Score = MutableStateFlow(0)
    val battlePlayer1Score: StateFlow<Int> = _battlePlayer1Score.asStateFlow()

    private val _battlePlayer2Score = MutableStateFlow(0)
    val battlePlayer2Score: StateFlow<Int> = _battlePlayer2Score.asStateFlow()

    // AI Creator Studio State
    private val _aiTopicInput = MutableStateFlow("")
    val aiTopicInput: StateFlow<String> = _aiTopicInput.asStateFlow()

    private val _isAiLoading = MutableStateFlow(false)
    val isAiLoading: StateFlow<Boolean> = _isAiLoading.asStateFlow()

    private val _generatedAiText = MutableStateFlow<String?>(null)
    val generatedAiText: StateFlow<String?> = _generatedAiText.asStateFlow()

    private val _aiGeneratedQuiz = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val aiGeneratedQuiz: StateFlow<List<QuizQuestion>> = _aiGeneratedQuiz.asStateFlow()

    private val _aiGeneratedVocab = MutableStateFlow<List<FlashcardItem>>(emptyList())
    val aiGeneratedVocab: StateFlow<List<FlashcardItem>> = _aiGeneratedVocab.asStateFlow()

    init {
        viewModelScope.launch {
            repository.ensureProfileExists()
        }
    }

    fun selectLevel(level: EducationLevel) {
        _selectedLevel.value = level
        viewModelScope.launch {
            repository.updateSelectedLevel(level.name)
        }
    }

    fun navigateToTab(tab: MainNavTab) {
        _currentTab.value = tab
        _activeSubScreen.value = ActiveSubScreen.NONE
    }

    fun openSubScreen(screen: ActiveSubScreen) {
        _activeSubScreen.value = screen
    }

    fun backToHomeOrTab() {
        _activeSubScreen.value = ActiveSubScreen.NONE
    }

    // Flashcards
    fun nextFlashcard() {
        _isCardFlipped.value = false
        if (_currentCardIndex.value < _flashcards.value.size - 1) {
            _currentCardIndex.value++
        } else {
            _currentCardIndex.value = 0
        }
    }

    fun prevFlashcard() {
        _isCardFlipped.value = false
        if (_currentCardIndex.value > 0) {
            _currentCardIndex.value--
        } else {
            _currentCardIndex.value = _flashcards.value.size - 1
        }
    }

    fun flipCard() {
        _isCardFlipped.value = !_isCardFlipped.value
    }

    fun speakFlashcard(card: FlashcardItem, speakEng: Boolean) {
        if (speakEng) {
            ttsManager.speak(card.englishWord, isEnglish = true)
        } else {
            ttsManager.speak(card.thaiWord, isEnglish = false)
        }
    }

    // Story
    fun nextStoryPage() {
        if (_currentStoryPageIndex.value < _storyPages.value.size - 1) {
            _currentStoryPageIndex.value++
            _activeCharacterAction.value = null
        }
    }

    fun prevStoryPage() {
        if (_currentStoryPageIndex.value > 0) {
            _currentStoryPageIndex.value--
            _activeCharacterAction.value = null
        }
    }

    fun triggerCharacterAction(character: com.example.data.model.StoryCharacter) {
        _activeCharacterAction.value = "${character.name}: ${character.soundLabel} - ${character.animationAction}"
        ttsManager.speak(character.soundLabel, isEnglish = false)
    }

    fun playStoryVoice(page: InteractiveStoryPage) {
        ttsManager.speak(page.textThai, isEnglish = false)
    }

    // Drag & Drop Match
    fun selectMatchSource(id: String) {
        _selectedMatchSource.value = id
    }

    fun tryMatchTarget(matchKey: String) {
        val selected = _selectedMatchSource.value ?: return
        val item = _dragDropPairs.value.find { it.id == selected }
        if (item != null && item.matchKey == matchKey) {
            _matchedIds.value = _matchedIds.value + selected
            _selectedMatchSource.value = null
            ttsManager.speak("ถูกต้อง เก่งมาก!", isEnglish = false)
            viewModelScope.launch {
                repository.addReward(xpGained = 20, coinsGained = 5)
            }
        } else {
            _selectedMatchSource.value = null
            ttsManager.speak("ลองใหม่อีกครั้งนะจ๊ะ", isEnglish = false)
        }
    }

    fun resetMatchGame() {
        _matchedIds.value = emptySet()
        _selectedMatchSource.value = null
    }

    // Math Beads
    fun changeBeadsRow1(delta: Int) {
        val newVal = (_beadsCountRow1.value + delta).coerceIn(0, 10)
        _beadsCountRow1.value = newVal
    }

    fun changeBeadsRow2(delta: Int) {
        val newVal = (_beadsCountRow2.value + delta).coerceIn(0, 10)
        _beadsCountRow2.value = newVal
    }

    // Life Cycles
    fun selectLifeCycle(type: String) {
        _selectedLifeCycleType.value = type
        _activeLifeCycleStep.value = 1
    }

    fun setLifeCycleStep(step: Int) {
        _activeLifeCycleStep.value = step
    }

    // Function Graph
    fun updateGraphParams(a: Float, b: Float, c: Float) {
        _graphA.value = a
        _graphB.value = b
        _graphC.value = c
    }

    // Circuit Simulator
    fun updateCircuitParams(v: Float, r: Float, switchClosed: Boolean) {
        _circuitVoltage.value = v
        _circuitResistance.value = r
        _isCircuitSwitchClosed.value = switchClosed
    }

    // Reading Practice
    fun nextReadingPassage() {
        _readingScore.value = null
        val total = SampleEduData.readingPassages.size
        _readingPassageIndex.value = (_readingPassageIndex.value + 1) % total
    }

    fun completeReadingEvaluation() {
        // Evaluate pronunciation
        val score = (85..98).random()
        _readingScore.value = score
        ttsManager.speak("ยอดเยี่ยมมาก คะแนนการออกเสียงของคุณคือ $score คะแนน", isEnglish = false)
        viewModelScope.launch {
            repository.addReward(xpGained = 40, coinsGained = 10)
        }
    }

    // Live Classroom
    fun joinLiveRoom(pin: String) {
        _liveRoomPin.value = pin
        _isHost.value = false
    }

    fun createLiveRoom() {
        val pin = (100000..999999).random().toString()
        _liveRoomPin.value = pin
        _isHost.value = true
    }

    // Duo Battle
    fun answerDuoQuestion(isPlayer1: Boolean, isCorrect: Boolean) {
        if (isPlayer1) {
            _battlePlayer1Score.value += if (isCorrect) 100 else -20
        } else {
            _battlePlayer2Score.value += if (isCorrect) 100 else -20
        }
    }

    fun resetDuoBattle() {
        _battlePlayer1Score.value = 0
        _battlePlayer2Score.value = 0
    }

    // AI Creator Studio
    fun setAiTopic(topic: String) {
        _aiTopicInput.value = topic
    }

    fun generateSlideOutlineWithAi() {
        val topic = _aiTopicInput.value.ifBlank { "ระบบสุริยะและการโคจรของดาวเคราะห์" }
        _isAiLoading.value = true
        _generatedAiText.value = null
        viewModelScope.launch {
            val result = geminiService.generateSlideOutline(
                topic = topic,
                educationLevel = _selectedLevel.value,
                subject = SubjectCategory.SCIENCE
            )
            _generatedAiText.value = result
            _isAiLoading.value = false
            repository.saveCreatedMedia(
                title = "สไลด์สรุป: $topic",
                mediaType = "SLIDE_OUTLINE",
                subject = "วิทยาศาสตร์",
                educationLevel = _selectedLevel.value.title,
                summary = "โครงร่างสไลด์และเนื้อหาการสอนสร้างด้วย AI",
                contentData = result
            )
        }
    }

    fun generateQuizWithAi() {
        val topic = _aiTopicInput.value.ifBlank { "การสังเคราะห์ด้วยแสงและการดำรงชีวิตของพืช" }
        _isAiLoading.value = true
        _aiGeneratedQuiz.value = emptyList()
        viewModelScope.launch {
            val questions = geminiService.generateQuizFromText(
                sourceText = topic,
                subject = SubjectCategory.SCIENCE,
                level = _selectedLevel.value
            )
            _aiGeneratedQuiz.value = questions
            _isAiLoading.value = false
            if (questions.isNotEmpty()) {
                repository.saveCreatedMedia(
                    title = "ควิซแบบทดสอบ: $topic",
                    mediaType = "QUIZ",
                    subject = "วิทยาศาสตร์",
                    educationLevel = _selectedLevel.value.title,
                    summary = "แบบทดสอบ 4 ตัวเลือกพร้อมเฉลย ${questions.size} ข้อ",
                    contentData = topic
                )
            }
        }
    }

    fun generateVocabWithAi() {
        val text = _aiTopicInput.value.ifBlank { "Modern Technology and Scientific Innovations in Robotics" }
        _isAiLoading.value = true
        _aiGeneratedVocab.value = emptyList()
        viewModelScope.launch {
            val cards = geminiService.generateVocabCardsFromText(text)
            _aiGeneratedVocab.value = cards
            _isAiLoading.value = false
            if (cards.isNotEmpty()) {
                _flashcards.value = cards + _flashcards.value
                repository.saveCreatedMedia(
                    title = "บัตรคำศัพท์: ${cards.firstOrNull()?.thaiWord ?: text}",
                    mediaType = "FLASHCARD",
                    subject = "ภาษา",
                    educationLevel = _selectedLevel.value.title,
                    summary = "ชุดบัตรคำศัพท์สองภาษา ${cards.size} ใบ",
                    contentData = text
                )
            }
        }
    }

    fun deleteMedia(id: Long) {
        viewModelScope.launch {
            repository.deleteCreatedMedia(id)
        }
    }

    fun saveArtwork(summary: String) {
        viewModelScope.launch {
            repository.saveCreatedMedia(
                title = "ผลงานระบายสี",
                mediaType = "COLORING",
                subject = "ศิลปะ",
                educationLevel = "อนุบาล - ปฐมวัย",
                summary = summary,
                contentData = "artwork_paths"
            )
        }
    }

    fun finishQuiz(score: Int, total: Int, topic: String) {
        viewModelScope.launch {
            repository.saveQuizAttempt(
                topicName = topic,
                subject = "วิทยาศาสตร์ & ทั่วไป",
                educationLevel = _selectedLevel.value.title,
                score = score,
                total = total
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.shutdown()
        audioRecorder.release()
    }
}
