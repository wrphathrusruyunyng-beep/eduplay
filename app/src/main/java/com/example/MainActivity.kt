package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.data.model.SampleEduData
import com.example.ui.ActiveSubScreen
import com.example.ui.MainEduViewModel
import com.example.ui.MainNavTab
import com.example.ui.components.EduBottomNav
import com.example.ui.components.EduTopBar
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ModulesScreen
import com.example.ui.screens.analytics.LearningAnalyticsScreen
import com.example.ui.screens.gamification.GamesScreen
import com.example.ui.screens.kindergarten.ColoringCanvasScreen
import com.example.ui.screens.kindergarten.DragDropMatchingScreen
import com.example.ui.screens.kindergarten.FlashcardsScreen
import com.example.ui.screens.kindergarten.InteractiveStoryScreen
import com.example.ui.screens.primary.InteractiveMathScienceScreen
import com.example.ui.screens.primary.PrimaryQuizScreen
import com.example.ui.screens.primary.ReadingPracticeScreen
import com.example.ui.screens.secondary.ExamDiagnosticScreen
import com.example.ui.screens.secondary.MindMapScreen
import com.example.ui.screens.secondary.VirtualLabScreen
import com.example.ui.screens.studio.CreatorStudioScreen
import com.example.ui.screens.vocational.PortfolioRubricScreen
import com.example.ui.screens.vocational.TechnicalGuideScreen
import com.example.ui.theme.EduPlayTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainEduViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            EduPlayTheme {
                MainAppScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppScreen(viewModel: MainEduViewModel) {
    val selectedLevel by viewModel.selectedLevel.collectAsState()
    val currentTab by viewModel.currentTab.collectAsState()
    val activeSubScreen by viewModel.activeSubScreen.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    val createdMediaList by viewModel.createdMediaList.collectAsState()
    val quizAttempts by viewModel.quizAttempts.collectAsState()

    // Flashcard states
    val flashcards by viewModel.flashcards.collectAsState()
    val currentCardIndex by viewModel.currentCardIndex.collectAsState()
    val isCardFlipped by viewModel.isCardFlipped.collectAsState()

    // Story states
    val storyPages by viewModel.storyPages.collectAsState()
    val currentStoryPageIndex by viewModel.currentStoryPageIndex.collectAsState()
    val activeCharacterAction by viewModel.activeCharacterAction.collectAsState()

    // Drag Drop match states
    val dragDropPairs by viewModel.dragDropPairs.collectAsState()
    val matchedIds by viewModel.matchedIds.collectAsState()
    val selectedMatchSource by viewModel.selectedMatchSource.collectAsState()

    // Math & Science states
    val beadsRow1 by viewModel.beadsCountRow1.collectAsState()
    val beadsRow2 by viewModel.beadsCountRow2.collectAsState()
    val selectedLifeCycleType by viewModel.selectedLifeCycleType.collectAsState()
    val activeLifeCycleStep by viewModel.activeLifeCycleStep.collectAsState()

    // Function Graph states
    val graphA by viewModel.graphA.collectAsState()
    val graphB by viewModel.graphB.collectAsState()
    val graphC by viewModel.graphC.collectAsState()

    // Circuit states
    val circuitVoltage by viewModel.circuitVoltage.collectAsState()
    val circuitResistance by viewModel.circuitResistance.collectAsState()
    val isCircuitSwitchClosed by viewModel.isCircuitSwitchClosed.collectAsState()

    // Reading Practice states
    val readingPassageIndex by viewModel.readingPassageIndex.collectAsState()
    val readingScore by viewModel.readingScore.collectAsState()
    val isRecording by viewModel.audioRecorder.isRecording.collectAsState()
    val isPlaying by viewModel.audioRecorder.isPlaying.collectAsState()
    val hasRecording by viewModel.audioRecorder.hasRecording.collectAsState()

    // Live Room & Duo Battle states
    val liveRoomPin by viewModel.liveRoomPin.collectAsState()
    val isHost by viewModel.isHost.collectAsState()
    val livePlayers by viewModel.livePlayers.collectAsState()
    val player1Score by viewModel.battlePlayer1Score.collectAsState()
    val player2Score by viewModel.battlePlayer2Score.collectAsState()

    // AI Creator Studio states
    val aiTopicInput by viewModel.aiTopicInput.collectAsState()
    val isAiLoading by viewModel.isAiLoading.collectAsState()
    val generatedAiText by viewModel.generatedAiText.collectAsState()
    val aiGeneratedQuiz by viewModel.aiGeneratedQuiz.collectAsState()
    val aiGeneratedVocab by viewModel.aiGeneratedVocab.collectAsState()

    // Handle Hardware/System Back Button
    if (activeSubScreen != ActiveSubScreen.NONE) {
        BackHandler {
            viewModel.backToHomeOrTab()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().testTag("app_scaffold"),
        topBar = {
            if (activeSubScreen == ActiveSubScreen.NONE) {
                EduTopBar(
                    selectedLevel = selectedLevel,
                    userProfile = userProfile,
                    onSelectLevel = { viewModel.selectLevel(it) }
                )
            }
        },
        bottomBar = {
            if (activeSubScreen == ActiveSubScreen.NONE) {
                EduBottomNav(
                    currentTab = currentTab,
                    onTabSelected = { viewModel.navigateToTab(it) }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (activeSubScreen != ActiveSubScreen.NONE) {
                // Secondary / Sub-screen view
                when (activeSubScreen) {
                    ActiveSubScreen.KINDERGARTEN_FLASHCARDS -> {
                        FlashcardsScreen(
                            flashcards = flashcards,
                            currentIndex = currentCardIndex,
                            isFlipped = isCardFlipped,
                            onFlip = { viewModel.flipCard() },
                            onNext = { viewModel.nextFlashcard() },
                            onPrev = { viewModel.prevFlashcard() },
                            onSpeak = { card, isEng -> viewModel.speakFlashcard(card, isEng) },
                            onBack = { viewModel.backToHomeOrTab() }
                        )
                    }

                    ActiveSubScreen.KINDERGARTEN_STORY -> {
                        InteractiveStoryScreen(
                            storyPages = storyPages,
                            currentPageIndex = currentStoryPageIndex,
                            activeCharacterAction = activeCharacterAction,
                            onTriggerCharacter = { viewModel.triggerCharacterAction(it) },
                            onPlayStoryVoice = { viewModel.playStoryVoice(it) },
                            onNextPage = { viewModel.nextStoryPage() },
                            onPrevPage = { viewModel.prevStoryPage() },
                            onBack = { viewModel.backToHomeOrTab() }
                        )
                    }

                    ActiveSubScreen.KINDERGARTEN_DRAG_DROP -> {
                        DragDropMatchingScreen(
                            pairs = dragDropPairs,
                            matchedIds = matchedIds,
                            selectedSourceId = selectedMatchSource,
                            onSelectSource = { viewModel.selectMatchSource(it) },
                            onTryMatchTarget = { viewModel.tryMatchTarget(it) },
                            onReset = { viewModel.resetMatchGame() },
                            onBack = { viewModel.backToHomeOrTab() }
                        )
                    }

                    ActiveSubScreen.KINDERGARTEN_COLORING -> {
                        ColoringCanvasScreen(
                            onSaveArtwork = { summary ->
                                viewModel.saveArtwork(summary)
                            },
                            onBack = { viewModel.backToHomeOrTab() }
                        )
                    }

                    ActiveSubScreen.PRIMARY_QUIZ -> {
                        PrimaryQuizScreen(
                            questions = SampleEduData.quizQuestions.filter { it.level == selectedLevel }.ifEmpty { SampleEduData.quizQuestions },
                            onFinishQuiz = { score, total, topic ->
                                viewModel.finishQuiz(score, total, topic)
                            },
                            onSpeakQuestion = { viewModel.ttsManager.speak(it, false) },
                            onBack = { viewModel.backToHomeOrTab() }
                        )
                    }

                    ActiveSubScreen.PRIMARY_MATH_SCIENCE -> {
                        InteractiveMathScienceScreen(
                            beadsRow1 = beadsRow1,
                            beadsRow2 = beadsRow2,
                            selectedLifeCycleType = selectedLifeCycleType,
                            activeLifeCycleStep = activeLifeCycleStep,
                            onChangeBeadsRow1 = { viewModel.changeBeadsRow1(it) },
                            onChangeBeadsRow2 = { viewModel.changeBeadsRow2(it) },
                            onSelectLifeCycleType = { viewModel.selectLifeCycle(it) },
                            onSetLifeCycleStep = { viewModel.setLifeCycleStep(it) },
                            onBack = { viewModel.backToHomeOrTab() }
                        )
                    }

                    ActiveSubScreen.PRIMARY_READING_PRACTICE -> {
                        ReadingPracticeScreen(
                            passageIndex = readingPassageIndex,
                            isRecording = isRecording,
                            isPlaying = isPlaying,
                            hasRecording = hasRecording,
                            readingScore = readingScore,
                            onStartRecording = { viewModel.audioRecorder.startRecording() },
                            onStopRecording = { viewModel.audioRecorder.stopRecording() },
                            onPlayRecording = { viewModel.audioRecorder.playRecording() },
                            onStopPlaying = { viewModel.audioRecorder.stopPlaying() },
                            onSpeakPassage = { viewModel.ttsManager.speak(it, false) },
                            onEvaluate = { viewModel.completeReadingEvaluation() },
                            onNextPassage = { viewModel.nextReadingPassage() },
                            onBack = { viewModel.backToHomeOrTab() }
                        )
                    }

                    ActiveSubScreen.SECONDARY_MIND_MAP -> {
                        MindMapScreen(onBack = { viewModel.backToHomeOrTab() })
                    }

                    ActiveSubScreen.SECONDARY_VIRTUAL_LAB -> {
                        VirtualLabScreen(
                            graphA = graphA,
                            graphB = graphB,
                            graphC = graphC,
                            voltage = circuitVoltage,
                            resistance = circuitResistance,
                            isSwitchClosed = isCircuitSwitchClosed,
                            onUpdateGraphParams = { a, b, c -> viewModel.updateGraphParams(a, b, c) },
                            onUpdateCircuitParams = { v, r, closed -> viewModel.updateCircuitParams(v, r, closed) },
                            onBack = { viewModel.backToHomeOrTab() }
                        )
                    }

                    ActiveSubScreen.SECONDARY_EXAM_DIAGNOSTIC -> {
                        ExamDiagnosticScreen(
                            onStartExamPractice = {
                                viewModel.openSubScreen(ActiveSubScreen.PRIMARY_QUIZ)
                            },
                            onBack = { viewModel.backToHomeOrTab() }
                        )
                    }

                    ActiveSubScreen.VOCATIONAL_TECH_GUIDE -> {
                        TechnicalGuideScreen(onBack = { viewModel.backToHomeOrTab() })
                    }

                    ActiveSubScreen.VOCATIONAL_PORTFOLIO_RUBRIC -> {
                        PortfolioRubricScreen(onBack = { viewModel.backToHomeOrTab() })
                    }

                    else -> {
                        viewModel.backToHomeOrTab()
                    }
                }
            } else {
                // Tab Root View
                when (currentTab) {
                    MainNavTab.HOME -> {
                        HomeScreen(
                            selectedLevel = selectedLevel,
                            userProfile = userProfile,
                            onSelectLevel = { viewModel.selectLevel(it) },
                            onOpenSubScreen = { viewModel.openSubScreen(it) },
                            onNavigateTab = { viewModel.navigateToTab(it) }
                        )
                    }

                    MainNavTab.MODULES -> {
                        ModulesScreen(
                            selectedLevel = selectedLevel,
                            onSelectLevel = { viewModel.selectLevel(it) },
                            onOpenSubScreen = { viewModel.openSubScreen(it) }
                        )
                    }

                    MainNavTab.STUDIO -> {
                        CreatorStudioScreen(
                            createdMediaList = createdMediaList,
                            aiTopicInput = aiTopicInput,
                            isAiLoading = isAiLoading,
                            generatedAiText = generatedAiText,
                            aiGeneratedQuiz = aiGeneratedQuiz,
                            aiGeneratedVocab = aiGeneratedVocab,
                            onAiTopicChange = { viewModel.setAiTopic(it) },
                            onGenerateSlideOutline = { viewModel.generateSlideOutlineWithAi() },
                            onGenerateQuiz = { viewModel.generateQuizWithAi() },
                            onGenerateVocab = { viewModel.generateVocabWithAi() },
                            onDeleteMedia = { viewModel.deleteMedia(it) }
                        )
                    }

                    MainNavTab.GAMES -> {
                        GamesScreen(
                            userProfile = userProfile,
                            livePin = liveRoomPin,
                            isHost = isHost,
                            livePlayers = livePlayers,
                            player1Score = player1Score,
                            player2Score = player2Score,
                            onJoinRoom = { viewModel.joinLiveRoom(it) },
                            onCreateRoom = { viewModel.createLiveRoom() },
                            onAnswerDuo = { isP1, correct -> viewModel.answerDuoQuestion(isP1, correct) },
                            onResetDuo = { viewModel.resetDuoBattle() }
                        )
                    }

                    MainNavTab.ANALYTICS -> {
                        LearningAnalyticsScreen(
                            quizAttempts = quizAttempts,
                            selectedLevel = selectedLevel
                        )
                    }
                }
            }
        }
    }
}
