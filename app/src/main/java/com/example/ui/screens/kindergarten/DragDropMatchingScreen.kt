package com.example.ui.screens.kindergarten

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DragDropPair
import com.example.ui.theme.EduAmber
import com.example.ui.theme.EduEmerald
import com.example.ui.theme.EduIndigo

@Composable
fun DragDropMatchingScreen(
    pairs: List<DragDropPair>,
    matchedIds: Set<String>,
    selectedSourceId: String?,
    onSelectSource: (String) -> Unit,
    onTryMatchTarget: (String) -> Unit,
    onReset: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isAllMatched = matchedIds.size == pairs.size && pairs.isNotEmpty()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top navigation
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilledTonalButton(
                onClick = onBack,
                modifier = Modifier.testTag("drag_drop_back_button")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("กลับ")
            }

            Text(
                text = "จับคู่สำเร็จ: ${matchedIds.size} / ${pairs.size}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = EduIndigo
                )
            )

            OutlinedButton(
                onClick = onReset,
                shape = CircleShape,
                modifier = Modifier.testTag("reset_match_game_button")
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("เริ่มใหม่")
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Instruction card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "🎯 วิธีเล่นจับคู่สัตว์แสนรู้:",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFB45309)
                )
                Text(
                    text = "1. แตะเลือก 'ไอคอนสัตว์' ทางซ้าย\n2. แตะจับคู่กับ 'เสียงร้อง/ลักษณะ' ทางขวาให้ถูกต้อง!",
                    fontSize = 13.sp,
                    color = Color(0xFF92400E)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // All Matched Victory Card
        AnimatedVisibility(
            visible = isAllMatched,
            enter = fadeIn() + scaleIn()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD1FAE5)),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "🎉 ไชโย! เก่งมากจ้า! 🎉", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = EduEmerald)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "จับคู่สัตว์ครบทุกตัวแล้ว รับ +100 XP และ +25 เหรียญ!",
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF065F46)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = onReset,
                        colors = ButtonDefaults.buttonColors(containerColor = EduEmerald),
                        shape = CircleShape
                    ) {
                        Text("เล่นอีกรอบ")
                    }
                }
            }
        }

        // 2 Columns: Left (Animals to pick), Right (Descriptions to match)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Column 1: Source Animals
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "เลือกสัตว์ 🐶",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )

                pairs.forEach { item ->
                    val isMatched = matchedIds.contains(item.id)
                    val isSelected = selectedSourceId == item.id

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = !isMatched) { onSelectSource(item.id) }
                            .testTag("source_item_${item.id}"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = when {
                                isMatched -> Color(0xFFD1FAE5)
                                isSelected -> Color(0xFFEEF2FF)
                                else -> MaterialTheme.colorScheme.surface
                            }
                        ),
                        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, EduIndigo) else null,
                        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = item.icon, fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.label,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (isMatched) Color(0xFF047857) else MaterialTheme.colorScheme.onSurface
                                )
                            }
                            if (isMatched) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Matched",
                                    tint = EduEmerald,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Column 2: Target Descriptions
            Column(
                modifier = Modifier.weight(1.2f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "จับคู่กับเสียง/คำ 🔊",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )

                pairs.shuffled().forEach { item ->
                    val isMatched = matchedIds.contains(item.id)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = !isMatched && selectedSourceId != null) {
                                onTryMatchTarget(item.matchKey)
                            }
                            .testTag("target_item_${item.matchKey}"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isMatched) Color(0xFFD1FAE5) else MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = item.description,
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                color = if (isMatched) Color(0xFF047857) else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}
