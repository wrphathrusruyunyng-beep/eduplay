package com.example.ui.screens.primary

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.QuizQuestion
import com.example.ui.theme.EduAmber
import com.example.ui.theme.EduEmerald
import com.example.ui.theme.EduIndigo
import com.example.ui.theme.EduRose
import kotlinx.coroutines.delay

@Composable
fun PrimaryQuizScreen(
    questions: List<QuizQuestion>,
    onFinishQuiz: (score: Int, total: Int, topic: String) -> Unit,
    onSpeakQuestion: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var isSubmitted by remember { mutableStateOf(false) }
    var score by remember { mutableIntStateOf(0) }
    var currentStreak by remember { mutableIntStateOf(0) }
    var timeLeftSeconds by remember { mutableIntStateOf(20) }
    var isTimerActive by remember { mutableStateOf(true) }

    val currentQuestion = questions.getOrNull(currentIndex)

    // Countdown Timer Effect
    LaunchedEffect(currentIndex, isSubmitted) {
        if (!isSubmitted) {
            timeLeftSeconds = 20
            isTimerActive = true
            while (timeLeftSeconds > 0 && isTimerActive) {
                delay(1000)
                timeLeftSeconds--
            }
            if (timeLeftSeconds == 0 && !isSubmitted) {
                // Time up, auto-submit wrong
                selectedOptionIndex = -1
                isSubmitted = true
                currentStreak = 0
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilledTonalButton(
                onClick = onBack,
                modifier = Modifier.testTag("quiz_back_button")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("กลับ")
            }

            // Streak & Score
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFFFFEDD5))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "🔥 Streak x$currentStreak",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFC2410C)
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFFFEF3C7))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "⭐ แต้ม: $score",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFB45309)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (currentQuestion == null) {
            // End of Quiz Summary
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "🏆 จบแบบทดสอบแล้ว! 🏆", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = EduIndigo)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "คะแนนที่ทำได้: $score จากทั้งหมด ${questions.size * 100} แต้ม",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            onFinishQuiz(score / 100, questions.size, "แบบทดสอบประถมศึกษา")
                            onBack()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = EduEmerald),
                        shape = CircleShape,
                        modifier = Modifier.testTag("save_quiz_score_button")
                    ) {
                        Text("บันทึกผลการเรียนรู้ & รับเหรียญ")
                    }
                }
            }
            return
        }

        // Timer bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Timer,
                contentDescription = "Timer",
                tint = if (timeLeftSeconds <= 5) EduRose else EduAmber,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            LinearProgressIndicator(
                progress = { timeLeftSeconds / 20f },
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp)
                    .clip(CircleShape),
                color = if (timeLeftSeconds <= 5) EduRose else EduAmber,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${timeLeftSeconds}s",
                fontWeight = FontWeight.Bold,
                color = if (timeLeftSeconds <= 5) EduRose else MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Question Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ข้อที่ ${currentIndex + 1} จาก ${questions.size}",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    IconButtonWithText(
                        icon = Icons.AutoMirrored.Filled.VolumeUp,
                        text = "อ่านโจทย์",
                        onClick = { onSpeakQuestion(currentQuestion.question) }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = currentQuestion.question,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        lineHeight = 24.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Options
        currentQuestion.options.forEachIndexed { index, optionText ->
            val isSelected = selectedOptionIndex == index
            val isCorrect = index == currentQuestion.correctIndex

            val optionColor = when {
                !isSubmitted && isSelected -> EduIndigo.copy(alpha = 0.15f)
                isSubmitted && isCorrect -> Color(0xFFD1FAE5)
                isSubmitted && isSelected && !isCorrect -> Color(0xFFFFE4E6)
                else -> MaterialTheme.colorScheme.surface
            }

            val borderColor = when {
                !isSubmitted && isSelected -> EduIndigo
                isSubmitted && isCorrect -> EduEmerald
                isSubmitted && isSelected && !isCorrect -> EduRose
                else -> Color.Transparent
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.5.dp, borderColor, RoundedCornerShape(16.dp))
                    .clickable(enabled = !isSubmitted) {
                        selectedOptionIndex = index
                    }
                    .testTag("quiz_option_$index"),
                colors = CardDefaults.cardColors(containerColor = optionColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) EduIndigo else MaterialTheme.colorScheme.surfaceVariant
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = ('A'.code + index).toChar().toString(),
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = optionText,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    if (isSubmitted) {
                        if (isCorrect) {
                            Icon(Icons.Default.Check, contentDescription = "Correct", tint = EduEmerald)
                        } else if (isSelected) {
                            Icon(Icons.Default.Close, contentDescription = "Wrong", tint = EduRose)
                        }
                    }
                }
            }
        }

        // Explanation & Next button
        AnimatedVisibility(visible = isSubmitted, enter = fadeIn()) {
            Column(modifier = Modifier.padding(top = 14.dp)) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEEF2FF))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "💡 คำอธิบายเฉลย:",
                            fontWeight = FontWeight.Bold,
                            color = EduIndigo
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = currentQuestion.explanation,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        currentIndex++
                        selectedOptionIndex = null
                        isSubmitted = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("quiz_next_question_button"),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = EduIndigo)
                ) {
                    Text("ข้อถัดไป", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
                }
            }
        }

        if (!isSubmitted) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (selectedOptionIndex != null) {
                        isSubmitted = true
                        isTimerActive = false
                        if (selectedOptionIndex == currentQuestion.correctIndex) {
                            score += 100 + (currentStreak * 20)
                            currentStreak++
                        } else {
                            currentStreak = 0
                        }
                    }
                },
                enabled = selectedOptionIndex != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("quiz_submit_answer_button"),
                shape = CircleShape
            ) {
                Text("ยืนยันคำตอบ")
            }
        }
    }
}

@Composable
fun IconButtonWithText(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = text, tint = EduIndigo, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, fontSize = 11.sp, color = EduIndigo, fontWeight = FontWeight.SemiBold)
    }
}
