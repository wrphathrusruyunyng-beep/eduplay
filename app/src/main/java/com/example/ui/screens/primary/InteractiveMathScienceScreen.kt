package com.example.ui.screens.primary

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SampleEduData
import com.example.ui.theme.EduAmber
import com.example.ui.theme.EduEmerald
import com.example.ui.theme.EduIndigo
import com.example.ui.theme.EduTeal

@Composable
fun InteractiveMathScienceScreen(
    beadsRow1: Int,
    beadsRow2: Int,
    selectedLifeCycleType: String,
    activeLifeCycleStep: Int,
    onChangeBeadsRow1: (Int) -> Unit,
    onChangeBeadsRow2: (Int) -> Unit,
    onSelectLifeCycleType: (String) -> Unit,
    onSetLifeCycleStep: (Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Math Beads, 1 = Animal Life Cycle

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledTonalButton(
                onClick = onBack,
                modifier = Modifier.testTag("math_science_back_button")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("กลับ")
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Interactive Board คณิต & วิทย์ 📐",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Tab Selector
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.clip(RoundedCornerShape(14.dp))
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Calculate, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("แถบนับเลขลูกคิด", fontWeight = FontWeight.Bold)
                    }
                },
                modifier = Modifier.testTag("tab_math_beads")
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Park, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("วงจรชีวิตสัตว์", fontWeight = FontWeight.Bold)
                    }
                },
                modifier = Modifier.testTag("tab_life_cycle")
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        if (selectedTab == 0) {
            // Math Beads Counter
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "🧮 แถบนับเลขจำลอง (Interactive Abacus)",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = EduIndigo
                        )
                    )
                    Text(
                        text = "ฝึกนับจำนวนและการบวกเลขแบบเห็นภาพจริง แตะปุ่ม + หรือ - เพื่อเพิ่มลดลูกปัด",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Row 1
                    BeadRow(
                        label = "แถวที่ 1 (สีส้ม):",
                        count = beadsRow1,
                        color = EduAmber,
                        onIncrement = { onChangeBeadsRow1(1) },
                        onDecrement = { onChangeBeadsRow1(-1) },
                        rowTestTag = "beads_row_1"
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Row 2
                    BeadRow(
                        label = "แถวที่ 2 (สีฟ้า):",
                        count = beadsRow2,
                        color = EduTeal,
                        onIncrement = { onChangeBeadsRow2(1) },
                        onDecrement = { onChangeBeadsRow2(-1) },
                        rowTestTag = "beads_row_2"
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Equation Result Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFEEF2FF))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$beadsRow1 + $beadsRow2 = ${beadsRow1 + beadsRow2}",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = EduIndigo
                            )
                        )
                    }
                }
            }
        } else {
            // Animal Life Cycles
            val stages = if (selectedLifeCycleType == "butterfly") {
                SampleEduData.butterflyLifeCycle
            } else {
                SampleEduData.frogLifeCycle
            }

            // Life cycle switcher
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                listOf("butterfly" to "ผีเสื้อ 🦋", "frog" to "กบ 🐸").forEach { (type, label) ->
                    val isSelected = selectedLifeCycleType == type
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) EduEmerald else MaterialTheme.colorScheme.surfaceVariant)
                            .clickable { onSelectLifeCycleType(type) }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .testTag("lifecycle_type_$type")
                    ) {
                        Text(
                            text = label,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stage Steps Horizontal Selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                stages.forEach { stage ->
                    val isCurrent = stage.step == activeLifeCycleStep
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable { onSetLifeCycleStep(stage.step) }
                            .testTag("stage_step_${stage.step}")
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(if (isCurrent) EduEmerald else Color(0xFFE2E8F0)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = stage.emoji, fontSize = 22.sp)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "ระยะ ${stage.step}",
                            fontSize = 11.sp,
                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                            color = if (isCurrent) EduEmerald else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Active Stage Detailed Card
            val activeStage = stages.find { it.step == activeLifeCycleStep } ?: stages.first()
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = activeStage.emoji, fontSize = 64.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = activeStage.name,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = EduEmerald
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = activeStage.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 22.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFFEF3C7))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "🔍 เกร็ดน่ารู้: ${activeStage.funFact}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF92400E)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BeadRow(
    label: String,
    count: Int,
    color: Color,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    rowTestTag: String
) {
    Column(modifier = Modifier.testTag(rowTestTag)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$label $count ลูก", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                IconButton(
                    onClick = onDecrement,
                    enabled = count > 0,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Decrement", modifier = Modifier.size(16.dp))
                }
                IconButton(
                    onClick = onIncrement,
                    enabled = count < 10,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Increment", modifier = Modifier.size(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Beads wire visualization
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFF1F5F9))
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            // Horizontal wire
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(Color(0xFFCBD5E1))
            )

            // Beads balls
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(count) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }
        }
    }
}
