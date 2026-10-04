package com.example.ui.screens.primary

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SampleEduData
import com.example.ui.theme.EduEmerald
import com.example.ui.theme.EduIndigo
import com.example.ui.theme.EduRose
import kotlinx.coroutines.delay

@Composable
fun ReadingPracticeScreen(
    passageIndex: Int,
    isRecording: Boolean,
    isPlaying: Boolean,
    hasRecording: Boolean,
    readingScore: Int?,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    onPlayRecording: () -> Unit,
    onStopPlaying: () -> Unit,
    onSpeakPassage: (String) -> Unit,
    onEvaluate: () -> Unit,
    onNextPassage: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val passage = SampleEduData.readingPassages.getOrNull(passageIndex) ?: SampleEduData.readingPassages.first()
    var recordTimerSeconds by remember { mutableIntStateOf(0) }

    LaunchedEffect(isRecording) {
        if (isRecording) {
            recordTimerSeconds = 0
            while (true) {
                delay(1000)
                recordTimerSeconds++
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
        // Back header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilledTonalButton(
                onClick = onBack,
                modifier = Modifier.testTag("reading_back_button")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("กลับ")
            }

            Text(
                text = "บทที่ ${passageIndex + 1} / ${SampleEduData.readingPassages.size}",
                fontWeight = FontWeight.Bold,
                color = EduIndigo
            )

            Button(
                onClick = onNextPassage,
                shape = CircleShape,
                modifier = Modifier.testTag("next_passage_button")
            ) {
                Text("เปลี่ยนบท")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next", modifier = Modifier.size(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Passage Display Card
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
                Text(
                    text = "📖 ข้อความฝึกอ่านออกเสียง",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "\"$passage\"",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        lineHeight = 34.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Listen to master button
                OutlinedButton(
                    onClick = { onSpeakPassage(passage) },
                    shape = CircleShape,
                    modifier = Modifier.testTag("listen_master_reading_button")
                ) {
                    Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Listen Master", tint = EduIndigo)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("ฟังเสียงอ่านต้นแบบ (Master TTS)")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Recording Studio Deck
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = if (isRecording) Color(0xFFFFE4E6) else Color(0xFFF1F5F9))
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isRecording) {
                    Text(
                        text = "🔴 กำลังบันทึกเสียง... $recordTimerSeconds วินาที",
                        color = EduRose,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    // Simulated audio waveform
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        listOf(12.dp, 28.dp, 40.dp, 20.dp, 36.dp, 16.dp, 30.dp, 24.dp).forEach { h ->
                            Box(
                                modifier = Modifier
                                    .width(6.dp)
                                    .height(h)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(EduRose)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = onStopRecording,
                        colors = ButtonDefaults.buttonColors(containerColor = EduRose),
                        shape = CircleShape,
                        modifier = Modifier.testTag("stop_recording_button")
                    ) {
                        Icon(Icons.Default.Stop, contentDescription = "Stop Recording")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("หยุดบันทึกเสียง")
                    }
                } else {
                    Text(
                        text = if (hasRecording) "บันทึกเสียงเสร็จแล้ว! กดฟังหรือส่งตรวจได้เลย" else "กดปุ่มด้านล่างเพื่อเริ่มอัดเสียงอ่านของคุณ",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(
                            onClick = onStartRecording,
                            colors = ButtonDefaults.buttonColors(containerColor = EduRose),
                            shape = CircleShape,
                            modifier = Modifier.testTag("start_recording_button")
                        ) {
                            Icon(Icons.Default.Mic, contentDescription = "Start Mic")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (hasRecording) "อัดเสียงใหม่" else "เริ่มอัดเสียง")
                        }

                        if (hasRecording) {
                            OutlinedButton(
                                onClick = if (isPlaying) onStopPlaying else onPlayRecording,
                                shape = CircleShape,
                                modifier = Modifier.testTag("play_recording_button")
                            ) {
                                Icon(
                                    imageVector = if (isPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                                    contentDescription = "Play"
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(if (isPlaying) "หยุดเล่น" else "ฟังเสียงที่อัด")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Pronunciation Evaluation button
        if (hasRecording && !isRecording) {
            Button(
                onClick = onEvaluate,
                colors = ButtonDefaults.buttonColors(containerColor = EduEmerald),
                shape = CircleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("evaluate_pronunciation_button")
            ) {
                Icon(Icons.Default.Star, contentDescription = "Evaluate")
                Spacer(modifier = Modifier.width(8.dp))
                Text("ตรวจและประเมินการออกเสียง (+XP)", fontWeight = FontWeight.Bold)
            }
        }

        // Evaluation Result Card
        AnimatedVisibility(visible = readingScore != null, enter = fadeIn()) {
            readingScore?.let { scoreVal ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFD1FAE5))
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🌟 ผลการประเมินการอ่าน 🌟", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF065F46))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "$scoreVal / 100 คะแนน",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF047857)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "จังหวะการเว้นวรรคและการออกเสียง ร/ล และวรรณยุกต์ถูกต้องและชัดเจนดีมาก!",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFF065F46),
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
        }
    }
}
