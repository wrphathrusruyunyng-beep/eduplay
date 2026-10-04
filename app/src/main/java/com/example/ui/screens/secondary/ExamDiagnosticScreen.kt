package com.example.ui.screens.secondary

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EduAmber
import com.example.ui.theme.EduEmerald
import com.example.ui.theme.EduIndigo
import com.example.ui.theme.EduRose

data class WeakPointAnalysis(
    val topicName: String,
    val subject: String,
    val accuracyPercent: Int,
    val isWeak: Boolean,
    val recommendation: String
)

@Composable
fun ExamDiagnosticScreen(
    onStartExamPractice: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val diagnostics = listOf(
        WeakPointAnalysis(
            topicName = "ฟังก์ชันกำลังสองและพาราโบลา",
            subject = "คณิตศาสตร์ ม.ปลาย",
            accuracyPercent = 45,
            isWeak = true,
            recommendation = "ควรทบทวนการหาจุดยอด (h,k) และสูตรสมมาตร -x = b/2a"
        ),
        WeakPointAnalysis(
            topicName = "ไฟฟ้าและกฎของโอห์ม",
            subject = "ฟิสิกส์",
            accuracyPercent = 88,
            isWeak = false,
            recommendation = "ทำได้ดีมาก ความเข้าใจเรื่องวงจรและการคำนวณแม่นยำ"
        ),
        WeakPointAnalysis(
            topicName = "การสังเคราะห์ด้วยแสงและการหายใจระดับเซลล์",
            subject = "ชีววิทยา",
            accuracyPercent = 92,
            isWeak = false,
            recommendation = "ยอดเยี่ยม จดจำกระบวนการ Light Reaction ได้ครบถ้วน"
        ),
        WeakPointAnalysis(
            topicName = "ไวยากรณ์ Subject-Verb Agreement",
            subject = "ภาษาอังกฤษ (TGAT 1)",
            accuracyPercent = 55,
            isWeak = true,
            recommendation = "ระวังประธานที่เป็นเอกพจน์บุรุษที่ 3 และกลุ่มคำนามรวม (Collective Nouns)"
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Top row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledTonalButton(
                onClick = onBack,
                modifier = Modifier.testTag("exam_diagnostic_back_button")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("กลับ")
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "คลังข้อสอบ & วิเคราะห์จุดอ่อน 📊",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Weak spot urgent alert banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE4E6))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(EduRose),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Warning, contentDescription = "Warning", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "ตรวจพบ 2 จุดอ่อนที่ต้องเสริมความเข้าใจ!",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9F1239)
                    )
                    Text(
                        text = "คะแนนหัวข้อ 'ฟังก์ชันกำลังสอง' และ 'Grammar' ต่ำกว่า 60%",
                        fontSize = 12.sp,
                        color = Color(0xFF881337)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "วิเคราะห์ความแม่นยำรายหัวข้อ (Diagnostic Matrix)",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(10.dp))

        diagnostics.forEach { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = item.topicName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(text = item.subject, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (item.isWeak) Color(0xFFFFE4E6) else Color(0xFFD1FAE5))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "${item.accuracyPercent}%",
                                fontWeight = FontWeight.ExtraBold,
                                color = if (item.isWeak) EduRose else EduEmerald,
                                fontSize = 13.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { item.accuracyPercent / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(CircleShape),
                        color = if (item.isWeak) EduRose else EduEmerald,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "💡 ข้อเสนอแนะ: ${item.recommendation}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onStartExamPractice,
            shape = CircleShape,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("start_weak_spot_quiz_button")
        ) {
            Icon(Icons.Default.Analytics, contentDescription = "Practice")
            Spacer(modifier = Modifier.width(8.dp))
            Text("ทำข้อสอบชุดเฉพาะทางเพื่อเสริมจุดอ่อน (+100 XP)")
        }
    }
}
