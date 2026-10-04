package com.example.ui.screens.gamification

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
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.data.local.entities.UserProfileEntity
import com.example.data.model.LivePlayer
import com.example.ui.theme.EduAmber
import com.example.ui.theme.EduEmerald
import com.example.ui.theme.EduIndigo
import com.example.ui.theme.EduRose
import com.example.ui.theme.EduViolet

@Composable
fun GamesScreen(
    userProfile: UserProfileEntity?,
    livePin: String,
    isHost: Boolean,
    livePlayers: List<LivePlayer>,
    player1Score: Int,
    player2Score: Int,
    onJoinRoom: (String) -> Unit,
    onCreateRoom: () -> Unit,
    onAnswerDuo: (isPlayer1: Boolean, isCorrect: Boolean) -> Unit,
    onResetDuo: () -> Unit,
    modifier: Modifier = Modifier
) {
    var gameTab by remember { mutableIntStateOf(0) } // 0 = Live Classroom (PIN), 1 = Duo Battle, 2 = Badges & Streaks
    var inputPin by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tab Selector
        TabRow(
            selectedTabIndex = gameTab,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.clip(RoundedCornerShape(14.dp))
        ) {
            Tab(
                selected = gameTab == 0,
                onClick = { gameTab = 0 },
                text = { Text("ห้องสด (PIN)", fontWeight = FontWeight.Bold, fontSize = 12.sp) },
                modifier = Modifier.testTag("tab_live_room")
            )
            Tab(
                selected = gameTab == 1,
                onClick = { gameTab = 1 },
                text = { Text("ดวลสปีด 1v1", fontWeight = FontWeight.Bold, fontSize = 12.sp) },
                modifier = Modifier.testTag("tab_duo_battle")
            )
            Tab(
                selected = gameTab == 2,
                onClick = { gameTab = 2 },
                text = { Text("เข็มกลัด & XP", fontWeight = FontWeight.Bold, fontSize = 12.sp) },
                modifier = Modifier.testTag("tab_badges_xp")
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        when (gameTab) {
            0 -> {
                // Live Classroom (Kahoot style with PIN)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Sensors, contentDescription = "Live", tint = EduRose)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Live Classroom Mode (ตอบคำถามสด)",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Room PIN Display Box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFFEDE9FE))
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "GAME PIN CODE",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EduViolet
                                )
                                Text(
                                    text = livePin,
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 4.sp,
                                    color = EduViolet
                                )
                                Text(
                                    text = if (isHost) "👑 คุณเป็นผู้จัดห้อง (Host)" else "เข้าร่วมห้องแล้ว พร้อมแข่งขัน!",
                                    fontSize = 12.sp,
                                    color = Color(0xFF6B21A8)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Join / Create Room controls
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = inputPin,
                                onValueChange = { inputPin = it },
                                label = { Text("ใส่รหัส PIN 6 หลัก") },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("live_pin_input_field"),
                                singleLine = true
                            )
                            Button(
                                onClick = {
                                    if (inputPin.isNotBlank()) onJoinRoom(inputPin)
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = EduIndigo),
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .testTag("join_live_room_button")
                            ) {
                                Text("เข้าร่วม")
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedButton(
                            onClick = onCreateRoom,
                            shape = CircleShape,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("create_live_room_button")
                        ) {
                            Text("สร้างห้องใหม่สำหรับผู้สอน (Generate Host PIN)")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Real-time Leaderboard
                Text(
                    text = "🏆 ตารางคะแนนผู้เล่นในห้อง (Live Leaderboard):",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                livePlayers.sortedByDescending { it.score }.forEachIndexed { rank, player ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = when (rank) {
                                0 -> Color(0xFFFEF3C7)
                                1 -> Color(0xFFF1F5F9)
                                2 -> Color(0xFFFFEDD5)
                                else -> MaterialTheme.colorScheme.surface
                            }
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "#${rank + 1}",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 16.sp,
                                color = if (rank == 0) EduAmber else MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = player.avatar, fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = player.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text(text = "🔥 Streak x${player.streak}", fontSize = 11.sp, color = Color(0xFFEA580C))
                            }
                            Text(
                                text = "${player.score} pts",
                                fontWeight = FontWeight.ExtraBold,
                                color = EduIndigo,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            1 -> {
                // Duo Battle Speed Quiz
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "⚔️ ท้าดวลความเร็วตอบคำถาม (1v1 Duo Battle)",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = EduRose
                            )
                        )
                        Text(
                            text = "ใครตอบไวกว่าและแม่นยำกว่า ชนะการดวล!",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Score Comparison Bars
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "🧑 คุณ (Player 1)", fontWeight = FontWeight.Bold)
                                Text(
                                    text = "$player1Score",
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = EduIndigo
                                )
                            }
                            Text(
                                text = "VS",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = EduRose,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "🤖 AI Bot (Player 2)", fontWeight = FontWeight.Bold)
                                Text(
                                    text = "$player2Score",
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = EduViolet
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Duel Question Prompt
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFFF1F5F9))
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "คำถามดวลสปีด: '12 × 12 มีค่าเท่ากับเท่าใด?'",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Tap Answer Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { onAnswerDuo(true, true) },
                                colors = ButtonDefaults.buttonColors(containerColor = EduEmerald),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("duo_ans_144_button")
                            ) {
                                Text("144 (ถูก)")
                            }

                            Button(
                                onClick = { onAnswerDuo(true, false) },
                                colors = ButtonDefaults.buttonColors(containerColor = EduRose),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("duo_ans_124_button")
                            ) {
                                Text("124 (ผิด)")
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedButton(
                            onClick = onResetDuo,
                            shape = CircleShape,
                            modifier = Modifier.testTag("reset_duo_button")
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("เริ่มดวลใหม่")
                        }
                    }
                }
            }

            2 -> {
                // Badges & Streaks Profile
                val xp = userProfile?.xp ?: 350
                val level = userProfile?.level ?: 3
                val nextLevelXp = level * 150
                val progress = (xp % 150) / 150f

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(EduAmber),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "⭐", fontSize = 24.sp)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = userProfile?.displayName ?: "ผู้เรียนคนเก่ง",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "เลเวล $level • ผู้เรียนระดับก้าวหน้า",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "XP สะสม: $xp แต้ม", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            Text(text = "เป้าหมายถัดไป: $nextLevelXp XP", fontSize = 12.sp, color = EduIndigo)
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(CircleShape),
                            color = EduAmber,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "🎖️ เข็มกลัดเกียรติยศที่ปลดล็อกแล้ว (Achievements):",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                val badges = listOf(
                    Triple("ผู้สำรวจเสียงแรก", "ฟังเสียงคำศัพท์สองภาษาครบ 5 คำ", "🔊"),
                    Triple("สตรีค 3 วันติด", "เข้าเรียนรู้และทำแบบฝึกหัดต่อเนื่อง", "🔥"),
                    Triple("นักสร้างสรรค์สื่อ", "สร้างสื่อการสอนชิ้นแรกในสตูดิโอ", "🎨"),
                    Triple("แชมป์ห้องสด", "ติดอันดับท็อป 3 ในโหมด Kahoot Live", "🏆")
                )

                badges.forEach { (name, desc, icon) ->
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
                            Text(text = icon, fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(text = name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text(text = desc, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }
    }
}
