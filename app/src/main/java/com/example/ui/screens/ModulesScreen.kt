package com.example.ui.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.example.data.model.EducationLevel
import com.example.ui.ActiveSubScreen
import com.example.ui.theme.EduAmber
import com.example.ui.theme.EduEmerald
import com.example.ui.theme.EduIndigo
import com.example.ui.theme.EduRose
import com.example.ui.theme.EduTeal
import com.example.ui.theme.EduViolet

@Composable
fun ModulesScreen(
    selectedLevel: EducationLevel,
    onSelectLevel: (EducationLevel) -> Unit,
    onOpenSubScreen: (ActiveSubScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "คลังสื่อการเรียนรู้ตามระดับชั้น 🎓",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        Text(
            text = "เลือกดูและทดลองเล่นสื่อที่ออกแบบตรงตามพัฒนาการผู้เรียน",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Level Sections
        // Section 1: Kindergarten
        ModuleSectionCard(
            level = EducationLevel.KINDERGARTEN,
            icon = "👶",
            items = listOf(
                Triple("Flashcards สองภาษา", "ภาพคำศัพท์สัตว์ ผลไม้ เสียงพากย์ TTS", ActiveSubScreen.KINDERGARTEN_FLASHCARDS),
                Triple("นิทานอินเทอร์แอ็กทีฟ", "นิทานกดมีเสียง แอนิเมชันโต้ตอบ", ActiveSubScreen.KINDERGARTEN_STORY),
                Triple("เกมลากวางจับคู่สัตว์", "จับคู่สัตว์และเสียงร้องแสนสนุก", ActiveSubScreen.KINDERGARTEN_DRAG_DROP),
                Triple("กระดานระบายสี & สติกเกอร์", "วาดเขียน จานสีสดใส สติกเกอร์การ์ตูน", ActiveSubScreen.KINDERGARTEN_COLORING)
            ),
            onSelectLevel = onSelectLevel,
            onOpenSubScreen = onOpenSubScreen
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Section 2: Primary
        ModuleSectionCard(
            level = EducationLevel.PRIMARY,
            icon = "🎒",
            items = listOf(
                Triple("Quiz & Minigames มีจับเวลา", "จับเวลา 20 วิ สตรีคคูณแต้ม เหรียญทอง", ActiveSubScreen.PRIMARY_QUIZ),
                Triple("ลูกคิด & วงจรชีวิตสัตว์", "Interactive Board แถบนับเลข และวงจรผีเสื้อ/กบ", ActiveSubScreen.PRIMARY_MATH_SCIENCE),
                Triple("ฝึกอ่านออกเสียง (Voice Recorder)", "บันทึกเสียงอ่าน เปรียบเทียบกับ TTS และให้คะแนน", ActiveSubScreen.PRIMARY_READING_PRACTICE)
            ),
            onSelectLevel = onSelectLevel,
            onOpenSubScreen = onOpenSubScreen
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Section 3: Secondary
        ModuleSectionCard(
            level = EducationLevel.SECONDARY,
            icon = "🏛️",
            items = listOf(
                Triple("ผังมโนทัศน์ (Mind Map) สรุปบทเรียน", "แผนผังมโนทัศน์เชื่อมโยงระบบนิเวศ & DNA", ActiveSubScreen.SECONDARY_MIND_MAP),
                Triple("ห้องแล็บเสมือน (Interactive Lab)", "จำลองกราฟพาราโบลา & วงจรไฟฟ้ากฎของโอห์ม", ActiveSubScreen.SECONDARY_VIRTUAL_LAB),
                Triple("คลังข้อสอบ & วิเคราะห์จุดอ่อน", "O-NET / TGAT ข้อสอบจำลองพร้อม Matrix วิเคราะห์", ActiveSubScreen.SECONDARY_EXAM_DIAGNOSTIC)
            ),
            onSelectLevel = onSelectLevel,
            onOpenSubScreen = onOpenSubScreen
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Section 4: Vocational
        ModuleSectionCard(
            level = EducationLevel.VOCATIONAL,
            icon = "⚙️",
            items = listOf(
                Triple("คู่มือช่าง Step-by-Step Technical Guide", "ผังวงจรไฟฟ้า โค้ดบล็อก และเช็กลิสต์ความปลอดภัย", ActiveSubScreen.VOCATIONAL_TECH_GUIDE),
                Triple("บอร์ดส่งงาน & รูบริกคะแนน 4 มิติ", "ส่งโครงงาน พอร์ตโฟลิโอ และเกณฑ์ประเมิน", ActiveSubScreen.VOCATIONAL_PORTFOLIO_RUBRIC)
            ),
            onSelectLevel = onSelectLevel,
            onOpenSubScreen = onOpenSubScreen
        )
    }
}

@Composable
fun ModuleSectionCard(
    level: EducationLevel,
    icon: String,
    items: List<Triple<String, String, ActiveSubScreen>>,
    onSelectLevel: (EducationLevel) -> Unit,
    onOpenSubScreen: (ActiveSubScreen) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectLevel(level) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(level.colorHex).copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = icon, fontSize = 22.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = level.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(level.colorHex)
                        )
                    )
                    Text(
                        text = level.subtitle,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            items.forEach { (name, subtitle, subScreen) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable {
                            onSelectLevel(level)
                            onOpenSubScreen(subScreen)
                        }
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                        .testTag("module_item_${subScreen.name.lowercase()}"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = name, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        Text(text = subtitle, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Open",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
