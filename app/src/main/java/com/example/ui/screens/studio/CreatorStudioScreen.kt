package com.example.ui.screens.studio

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.ViewCarousel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entities.CreatedMediaEntity
import com.example.data.model.FlashcardItem
import com.example.data.model.QuizQuestion
import com.example.data.model.SampleEduData
import com.example.ui.theme.EduAmber
import com.example.ui.theme.EduEmerald
import com.example.ui.theme.EduIndigo
import com.example.ui.theme.EduViolet

data class InteractiveBlock(
    val id: String,
    val typeName: String,
    val icon: String,
    val content: String
)

@Composable
fun CreatorStudioScreen(
    createdMediaList: List<CreatedMediaEntity>,
    aiTopicInput: String,
    isAiLoading: Boolean,
    generatedAiText: String?,
    aiGeneratedQuiz: List<QuizQuestion>,
    aiGeneratedVocab: List<FlashcardItem>,
    onAiTopicChange: (String) -> Unit,
    onGenerateSlideOutline: () -> Unit,
    onGenerateQuiz: () -> Unit,
    onGenerateVocab: () -> Unit,
    onDeleteMedia: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var studioMode by remember { mutableIntStateOf(0) } // 0 = AI Assistant, 1 = No-Code Block Builder, 2 = Templates & Gallery
    val customBlocks = remember {
        mutableStateListOf(
            InteractiveBlock("b1", "กล่องคำถามชวนคิด (Interactive Question)", "❓", "ทำไมท้องฟ้าจึงเป็นสีฟ้าในตอนกลางวัน?"),
            InteractiveBlock("b2", "การ์ดภาพ & เสียง (Flashcard Block)", "🃏", "การสังเคราะห์ด้วยแสง (Photosynthesis)"),
            InteractiveBlock("b3", "จุดหยุดถามคั่นวิดีโอ (Video Stop Point)", "⏸️", "หยุดวิดีโอเพื่อทดสอบความจำเรื่องเซลล์พืช")
        )
    }

    var newBlockContent by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Banner Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.linearGradient(
                        listOf(EduIndigo, EduViolet)
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = "AI", tint = Color(0xFFFDE047))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Content Creator Studio",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "สร้างสื่อการสอนอินเทอร์แอ็กทีฟได้ง่ายดาย ด้วยเทมเพลต No-Code และขุมพลัง AI Assist",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.White.copy(alpha = 0.9f)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mode Navigation Pills
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                0 to "🤖 AI Assist สร้างสื่อ",
                1 to "🧱 No-Code Builder",
                2 to "📂 คลังสื่อของฉัน"
            ).forEach { (mode, label) ->
                val isSelected = studioMode == mode
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) EduIndigo else MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { studioMode = mode }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (studioMode) {
            0 -> {
                // AI Assistant Mode
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "🤖 AI ช่วยสร้างสื่ออัตโนมัติ (Gemini Powered)",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = EduIndigo
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "พิมพ์หัวข้อหรือเนื้อหาบทเรียนที่ต้องการ AI จะช่วยสร้างโครงร่างสไลด์ ข้อสอบปรนัย หรือการ์ดคำศัพท์ให้ทันที",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        OutlinedTextField(
                            value = aiTopicInput,
                            onValueChange = onAiTopicChange,
                            label = { Text("หัวข้อบทเรียน หรือ เนื้อหาข้อความ") },
                            placeholder = { Text("เช่น 'ระบบสุริยะจักรวาล', 'การต่อวงจรไฟฟ้า', หรือบทความภาษาอังกฤษ") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("ai_topic_input_field"),
                            maxLines = 3
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(text = "เลือกรูปแบบสื่อที่ต้องการให้ AI สร้าง:", fontWeight = FontWeight.Bold, fontSize = 12.sp)

                        Spacer(modifier = Modifier.height(8.dp))

                        // Action Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = onGenerateSlideOutline,
                                enabled = !isAiLoading,
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = EduIndigo),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("ai_generate_slides_button")
                            ) {
                                Text("📊 โครงร่างสไลด์", fontSize = 11.sp)
                            }

                            Button(
                                onClick = onGenerateQuiz,
                                enabled = !isAiLoading,
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = EduEmerald),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("ai_generate_quiz_button")
                            ) {
                                Text("📝 ข้อสอบ 4 ตัวเลือก", fontSize = 11.sp)
                            }

                            Button(
                                onClick = onGenerateVocab,
                                enabled = !isAiLoading,
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = EduAmber),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("ai_generate_vocab_button")
                            ) {
                                Text("🃏 การ์ดคำศัพท์", fontSize = 11.sp)
                            }
                        }

                        if (isAiLoading) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(color = EduIndigo, modifier = Modifier.size(24.dp))
                                Spacer(modifier = Modifier.width(10.dp))
                                Text("AI กำลังประมวลผลและสร้างสื่อ...", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }

                // AI Output Display
                generatedAiText?.let { outputText ->
                    Spacer(modifier = Modifier.height(14.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "ผลลัพธ์โครงร่างสื่อการสอน:", fontWeight = FontWeight.Bold, color = EduIndigo)
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFFD1FAE5))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text("บันทึกลงคลังแล้ว ✅", fontSize = 10.sp, color = Color(0xFF065F46), fontWeight = FontWeight.Bold)
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = outputText,
                                style = MaterialTheme.typography.bodySmall.copy(lineHeight = 20.sp)
                            )
                        }
                    }
                }

                // AI Generated Quiz preview
                if (aiGeneratedQuiz.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(text = "ข้อสอบที่สร้างโดย AI (${aiGeneratedQuiz.size} ข้อ):", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    aiGeneratedQuiz.forEachIndexed { qIdx, q ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(text = "ข้อ ${qIdx + 1}: ${q.question}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Spacer(modifier = Modifier.height(6.dp))
                                q.options.forEachIndexed { oIdx, opt ->
                                    val isAns = oIdx == q.correctIndex
                                    Text(
                                        text = "${('A'.code + oIdx).toChar()}. $opt ${if (isAns) "✅ (เฉลย)" else ""}",
                                        fontSize = 12.sp,
                                        color = if (isAns) EduEmerald else MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontWeight = if (isAns) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                }
            }

            1 -> {
                // No-Code Interactive Builder
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "🧱 No-Code Interactive Builder",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = EduIndigo
                            )
                        )
                        Text(
                            text = "ออกแบบสื่อการเรียนรู้แบบโต้ตอบได้ง่ายๆ เพียงเพิ่มบล็อกคำถาม จุดเชื่อมโยง หรือแผ่นการ์ด",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Block creator
                        OutlinedTextField(
                            value = newBlockContent,
                            onValueChange = { newBlockContent = it },
                            label = { Text("ข้อความสำหรับบล็อกใหม่") },
                            placeholder = { Text("เช่น คำถามชวนคิด หรือ ข้อความสรุป") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {
                                if (newBlockContent.isNotBlank()) {
                                    customBlocks.add(
                                        InteractiveBlock(
                                            id = "blk_${System.currentTimeMillis()}",
                                            typeName = "บล็อกเนื้อหาโต้ตอบ",
                                            icon = "💡",
                                            content = newBlockContent
                                        )
                                    )
                                    newBlockContent = ""
                                }
                            },
                            shape = CircleShape,
                            modifier = Modifier.testTag("add_custom_block_button")
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("เพิ่มบล็อกสื่อนี้")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "ลำดับบล็อกในบทเรียน (Interactive Canvas):", fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(8.dp))

                customBlocks.forEachIndexed { index, block ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = block.icon, fontSize = 26.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "บล็อกที่ ${index + 1}: ${block.typeName}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = EduIndigo
                                )
                                Text(
                                    text = block.content,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            IconButton(
                                onClick = { customBlocks.removeAt(index) },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Gray, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }

            2 -> {
                // Templates & Saved Media Gallery
                Text(text = "เทมเพลตแนะนำตามกลุ่มสาระวิชา:", fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(SampleEduData.creatorTemplates) { tmpl ->
                        Card(
                            modifier = Modifier
                                .width(220.dp)
                                .height(130.dp),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = tmpl.icon, fontSize = 24.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = tmpl.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        maxLines = 1
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = tmpl.description,
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 3
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "คลังสื่อที่บันทึกไว้ในอุปกรณ์ (${createdMediaList.size} รายการ):",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (createdMediaList.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "ยังไม่มีสื่อที่สร้าง ลองใช้ AI หรือ No-Code Builder สร้างสื่อชิ้นแรกของคุณเลย! 🎨",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    createdMediaList.forEach { media ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Folder, contentDescription = null, tint = EduIndigo)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = media.title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text(
                                        text = "${media.mediaType} • ${media.educationLevel}",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                IconButton(
                                    onClick = { onDeleteMedia(media.id) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Gray, modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
