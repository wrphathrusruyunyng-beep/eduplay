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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entities.UserProfileEntity
import com.example.data.model.EducationLevel
import com.example.ui.ActiveSubScreen
import com.example.ui.MainNavTab
import com.example.ui.theme.EduAmber
import com.example.ui.theme.EduEmerald
import com.example.ui.theme.EduIndigo
import com.example.ui.theme.EduIndigoLight
import com.example.ui.theme.EduRose
import com.example.ui.theme.EduTeal
import com.example.ui.theme.EduViolet

@Composable
fun HomeScreen(
    selectedLevel: EducationLevel,
    userProfile: UserProfileEntity?,
    onSelectLevel: (EducationLevel) -> Unit,
    onOpenSubScreen: (ActiveSubScreen) -> Unit,
    onNavigateTab: (MainNavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Hero Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(26.dp))
                .background(
                    Brush.linearGradient(
                        listOf(EduIndigo, EduIndigoLight, EduViolet)
                    )
                )
                .padding(22.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White.copy(alpha = 0.2f))
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "✨ แพลตฟอร์มการเรียนรู้ครบวงจร",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(text = "🔥 ${userProfile?.streakDays ?: 5} วันต่อเนื่อง", color = Color(0xFFFDE047), fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "ยินดีต้อนรับสู่ EduPlay!",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                )
                Text(
                    text = "สื่อการเรียนรู้แบบโต้ตอบ สนุก เข้าใจง่าย ตอบโจทย์ทุกช่วงวัย",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.9f)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = { onNavigateTab(MainNavTab.STUDIO) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = CircleShape,
                        modifier = Modifier.testTag("home_hero_studio_button")
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = EduIndigo, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("AI สร้างสื่อ", color = EduIndigo, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }

                    Button(
                        onClick = { onNavigateTab(MainNavTab.GAMES) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFBBF24)),
                        shape = CircleShape,
                        modifier = Modifier.testTag("home_hero_live_game_button")
                    ) {
                        Icon(Icons.Default.Sensors, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("ห้องสด Kahoot", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Grade Level Switcher Ribbon
        Text(
            text = "เลือกระดับชั้นการศึกษา (Age Modules):",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            EducationLevel.values().forEach { level ->
                val isSelected = level == selectedLevel
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSelectLevel(level) }
                        .testTag("home_level_tab_${level.name.lowercase()}"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) Color(level.colorHex) else MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = when (level) {
                                EducationLevel.KINDERGARTEN -> "👶"
                                EducationLevel.PRIMARY -> "🎒"
                                EducationLevel.SECONDARY -> "🏛️"
                                EducationLevel.VOCATIONAL -> "⚙️"
                            },
                            fontSize = 22.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = when (level) {
                                EducationLevel.KINDERGARTEN -> "ปฐมวัย"
                                EducationLevel.PRIMARY -> "ประถม"
                                EducationLevel.SECONDARY -> "มัธยม"
                                EducationLevel.VOCATIONAL -> "อาชีวะ"
                            },
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        // Dynamic Sub-features based on current selected level
        Text(
            text = "ฟีเจอร์เด่นสำหรับ: ${selectedLevel.title}",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(selectedLevel.colorHex)
            )
        )
        Text(
            text = selectedLevel.subtitle,
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )

        Spacer(modifier = Modifier.height(12.dp))

        when (selectedLevel) {
            EducationLevel.KINDERGARTEN -> {
                // Kindergarten Features
                FeatureLaunchCard(
                    title = "บัตรคำศัพท์สองภาษา (Flashcards)",
                    desc = "ภาพการ์ตูนสดใส พลิกดูคำศัพท์ พร้อมเสียงพากย์สังเคราะห์ TTS ภาษาไทย & อังกฤษ",
                    icon = "🃏",
                    badge = "ภาพ & เสียง",
                    badgeColor = EduAmber,
                    onClick = { onOpenSubScreen(ActiveSubScreen.KINDERGARTEN_FLASHCARDS) },
                    testTag = "launch_flashcards"
                )

                FeatureLaunchCard(
                    title = "นิทาน Interactive โต้ตอบได้",
                    desc = "แตะตัวละครกระต่ายกับเต่าเพื่อฟังเสียงร้อง ดูแอนิเมชัน และเล่นเสียงเล่าเรื่องอัตโนมัติ",
                    icon = "📖",
                    badge = "นิทานแอนิเมชัน",
                    badgeColor = EduEmerald,
                    onClick = { onOpenSubScreen(ActiveSubScreen.KINDERGARTEN_STORY) },
                    testTag = "launch_story"
                )

                FeatureLaunchCard(
                    title = "มินิเกมลากวางจับคู่สัตว์ (Drag & Drop)",
                    desc = "จับคู่ไอคอนสัตว์น่ารักกับเสียงร้องและลักษณะเด่น พร้อมเอฟเฟกต์สำเร็จ",
                    icon = "🎯",
                    badge = "สัมผัส & ลากวาง",
                    badgeColor = EduTeal,
                    onClick = { onOpenSubScreen(ActiveSubScreen.KINDERGARTEN_DRAG_DROP) },
                    testTag = "launch_drag_drop"
                )

                FeatureLaunchCard(
                    title = "กระดานวาดเขียน & ระบายสี (Coloring Canvas)",
                    desc = "จานสีสดใส สติกเกอร์การ์ตูน แปรงหลายขนาด บันทึกผลงานลงในคลังสื่อ",
                    icon = "🎨",
                    badge = "ศิลปะสร้างสรรค์",
                    badgeColor = EduRose,
                    onClick = { onOpenSubScreen(ActiveSubScreen.KINDERGARTEN_COLORING) },
                    testTag = "launch_coloring"
                )
            }

            EducationLevel.PRIMARY -> {
                // Primary School Features
                FeatureLaunchCard(
                    title = "Quiz & Minigames แข่งขันชิงแต้ม",
                    desc = "ระบบทำข้อสอบมีจับเวลา 20 วินาที นับ Streak สะสมเหรียญ และฟังเฉลยคำอธิบาย",
                    icon = "🎮",
                    badge = "เกม & แต้มคะแนน",
                    badgeColor = EduIndigo,
                    onClick = { onOpenSubScreen(ActiveSubScreen.PRIMARY_QUIZ) },
                    testTag = "launch_primary_quiz"
                )

                FeatureLaunchCard(
                    title = "Interactive Board คณิต & วิทยาศาสตร์",
                    desc = "ลูกคิดแถบนับเลขจำลอง + วงจรชีวิตสัตว์ (ผีเสื้อ & กบ) แบบเห็นขั้นตอนจริง",
                    icon = "🧮",
                    badge = "จำลองสถานการณ์",
                    badgeColor = EduTeal,
                    onClick = { onOpenSubScreen(ActiveSubScreen.PRIMARY_MATH_SCIENCE) },
                    testTag = "launch_math_science"
                )

                FeatureLaunchCard(
                    title = "ห้องบันทึกเสียงฝึกอ่านออกเสียง",
                    desc = "ฝึกอ่านข้อความภาษาไทย/อังกฤษ บันทึกเสียงพร้อมคลื่นเสียง และระบบประเมินดาว",
                    icon = "🎙️",
                    badge = "บันทึกเสียงอ่าน",
                    badgeColor = EduRose,
                    onClick = { onOpenSubScreen(ActiveSubScreen.PRIMARY_READING_PRACTICE) },
                    testTag = "launch_reading_practice"
                )
            }

            EducationLevel.SECONDARY -> {
                // Secondary School Features
                FeatureLaunchCard(
                    title = "ผังมโนทัศน์ (Mind Map) สรุปบทเรียน",
                    desc = "สร้างและสำรวจเครือข่ายเชื่อมโยงแนวคิด ระบบนิเวศ และเพิ่มโหนดสรุปเนื้อหาเองได้",
                    icon = "🧠",
                    badge = "แผนผังความคิด",
                    badgeColor = EduViolet,
                    onClick = { onOpenSubScreen(ActiveSubScreen.SECONDARY_MIND_MAP) },
                    testTag = "launch_mind_map"
                )

                FeatureLaunchCard(
                    title = "ห้องทดลองเสมือน (Interactive Lab)",
                    desc = "ตัวจำลองกราฟพาราโบลาแบบ Real-time + วงจรไฟฟ้าเสมือนตามกฎของโอห์ม (V = IR)",
                    icon = "🔬",
                    badge = "Virtual Lab",
                    badgeColor = EduIndigo,
                    onClick = { onOpenSubScreen(ActiveSubScreen.SECONDARY_VIRTUAL_LAB) },
                    testTag = "launch_virtual_lab"
                )

                FeatureLaunchCard(
                    title = "คลังข้อสอบ & วิเคราะห์จุดอ่อนรายบุคคล",
                    desc = "ชุดข้อสอบจำลอง O-NET / TGAT พร้อม Matrix วิเคราะห์เรื่องที่ต้องทบทวนเป็นพิเศษ",
                    icon = "📊",
                    badge = "เตรียมสอบ & วิเคราะห์",
                    badgeColor = EduEmerald,
                    onClick = { onOpenSubScreen(ActiveSubScreen.SECONDARY_EXAM_DIAGNOSTIC) },
                    testTag = "launch_exam_diagnostic"
                )
            }

            EducationLevel.VOCATIONAL -> {
                // Vocational & Higher Ed Features
                FeatureLaunchCard(
                    title = "คู่มือช่าง Step-by-Step Technical Guide",
                    desc = "ขั้นตอนปฏิบัติการต่อวงจรไฟฟ้าอุตสาหกรรม, โค้ดบล็อก REST API และเช็กลิสต์ความปลอดภัย",
                    icon = "⚙️",
                    badge = "ทักษะสายอาชีพ",
                    badgeColor = EduViolet,
                    onClick = { onOpenSubScreen(ActiveSubScreen.VOCATIONAL_TECH_GUIDE) },
                    testTag = "launch_technical_guide"
                )

                FeatureLaunchCard(
                    title = "บอร์ดส่งงาน & รูบริกคะแนน (Rubrics)",
                    desc = "รองรับการส่งโครงงานและพอร์ตโฟลิโอ ตรวจประเมินผลงาน 4 มิติพร้อมคำแนะนำอาจารย์",
                    icon = "📋",
                    badge = "ตรวจงาน & รูบริก",
                    badgeColor = EduIndigo,
                    onClick = { onOpenSubScreen(ActiveSubScreen.VOCATIONAL_PORTFOLIO_RUBRIC) },
                    testTag = "launch_portfolio_rubric"
                )
            }
        }
    }
}

@Composable
fun FeatureLaunchCard(
    title: String,
    desc: String,
    icon: String,
    badge: String,
    badgeColor: Color,
    onClick: () -> Unit,
    testTag: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { onClick() }
            .testTag(testTag),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(badgeColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, fontSize = 26.sp)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(badgeColor.copy(alpha = 0.15f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = badge,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = badgeColor
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    ),
                    maxLines = 2
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Open",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
