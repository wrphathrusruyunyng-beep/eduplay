package com.example.ui.screens.analytics

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.data.local.entities.QuizAttemptEntity
import com.example.data.model.EducationLevel
import com.example.data.model.SubjectCategory
import com.example.ui.theme.EduEmerald
import com.example.ui.theme.EduIndigo
import com.example.ui.theme.EduRose

@Composable
fun LearningAnalyticsScreen(
    quizAttempts: List<QuizAttemptEntity>,
    selectedLevel: EducationLevel,
    modifier: Modifier = Modifier
) {
    var selectedCurriculumGroup by remember { mutableStateOf(SubjectCategory.SCIENCE) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Dashboard Title
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "แดชบอร์ดผลการเรียนรู้ 📊",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = "วิเคราะห์จุดที่เด็กในห้องตอบผิด & คัดกรองตามหลักสูตร",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Offline Cache status badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFD1FAE5))
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CloudDone, contentDescription = "Offline Cache", tint = EduEmerald, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Offline Cache", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF065F46))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Teacher Alert Card for Room
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEDD5))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFEA580C))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "สรุปสิ่งที่ครูผู้สอนต้องสอนซ้ำ (Needs Review):",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFC2410C),
                        fontSize = 13.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "• 68% ของนักเรียนสับสนเรื่อง 'การสังเคราะห์ด้วยแสง vs การหายใจระดับเซลล์'\n" +
                            "• 52% ยังคำนวณ 'การสลับเฟสมอเตอร์ไฟฟ้า 3 เฟส' ไม่ถูกต้อง",
                    fontSize = 12.sp,
                    color = Color(0xFF9A3412),
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Curriculum Filter Tabs
        Text(text = "คัดกรองตาม 8 กลุ่มสาระการเรียนรู้:", fontWeight = FontWeight.Bold, fontSize = 13.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            SubjectCategory.values().forEach { cat ->
                val isSelected = cat == selectedCurriculumGroup
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) EduIndigo else MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                        .testTag("filter_subject_${cat.name.lowercase()}"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${cat.icon} ${cat.title.take(6)}",
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Accuracy Breakdown
        Text(text = "สถิติอัตราความแม่นยำแยกตามสาระวิชา:", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        val subjectStats = listOf(
            Triple("วิทยาศาสตร์และเทคโนโลยี", 78, EduEmerald),
            Triple("คณิตศาสตร์ (เรขาคณิต/พีชคณิต)", 64, EduIndigo),
            Triple("ภาษาไทยและภาษาอังกฤษ", 82, Color(0xFF0284C7)),
            Triple("ช่างและทักษะสายอาชีพ", 75, Color(0xFF7C3AED))
        )

        subjectStats.forEach { (sub, pct, color) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = sub, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        Text(text = "$pct%", fontWeight = FontWeight.Bold, color = color, fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { pct / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(CircleShape),
                        color = color,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Recent Attempt Logs
        Text(text = "ประวัติการทำแบบทดสอบล่าสุด (Room Cache):", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        if (quizAttempts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "ยังไม่มีประวัติการสอบ ทำแบบทดสอบในหมวดประถม/มัธยม เพื่อเริ่มบันทึกสถิติ!",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            quizAttempts.take(5).forEach { attempt ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(if (attempt.isWeakTopic) EduRose else EduEmerald)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = attempt.topicName, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Text(text = "${attempt.subject} • ${attempt.educationLevel}", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Text(
                            text = "${attempt.score}/${attempt.totalQuestions} (${attempt.percentage}%)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = if (attempt.isWeakTopic) EduRose else EduEmerald
                        )
                    }
                }
            }
        }
    }
}
