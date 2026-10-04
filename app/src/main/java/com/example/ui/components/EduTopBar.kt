package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.data.local.entities.UserProfileEntity
import com.example.data.model.EducationLevel
import com.example.ui.theme.EduIndigo
import com.example.ui.theme.EduIndigoLight

@Composable
fun EduTopBar(
    selectedLevel: EducationLevel,
    userProfile: UserProfileEntity?,
    onSelectLevel: (EducationLevel) -> Unit,
    modifier: Modifier = Modifier
) {
    var expandedLevelMenu by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 3.dp,
        shadowElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Brand logo & title
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.testTag("app_brand_header")
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(EduIndigo, EduIndigoLight)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = "EduPlay App",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "EduPlay",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Text(
                            text = "สื่อการเรียนรู้อินเทอร์แอ็กทีฟ",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                // Stats Pills (Streak, XP, Coins)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Streak
                    StatBadge(
                        emoji = "🔥",
                        value = "${userProfile?.streakDays ?: 5} วัน",
                        bgColor = Color(0xFFFFEDD5),
                        textColor = Color(0xFFC2410C),
                        testTag = "streak_badge"
                    )

                    // XP
                    StatBadge(
                        emoji = "⭐",
                        value = "Lv.${userProfile?.level ?: 3}",
                        bgColor = Color(0xFFFEF3C7),
                        textColor = Color(0xFFB45309),
                        testTag = "level_badge"
                    )

                    // Coins
                    StatBadge(
                        emoji = "🪙",
                        value = "${userProfile?.coins ?: 120}",
                        bgColor = Color(0xFFCCFBF1),
                        textColor = Color(0xFF0F766E),
                        testTag = "coins_badge"
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Level Selector Chip Row
            Box {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(selectedLevel.colorHex).copy(alpha = 0.12f))
                        .clickable { expandedLevelMenu = true }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .testTag("level_selector_dropdown"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedLevel.badge,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(selectedLevel.colorHex)
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "• ${selectedLevel.subtitle}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        maxLines = 1
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Change Level",
                        tint = Color(selectedLevel.colorHex),
                        modifier = Modifier.size(20.dp)
                    )
                }

                DropdownMenu(
                    expanded = expandedLevelMenu,
                    onDismissRequest = { expandedLevelMenu = false }
                ) {
                    EducationLevel.values().forEach { level ->
                        DropdownMenuItem(
                            text = {
                                Column {
                                    Text(
                                        text = level.title,
                                        fontWeight = if (level == selectedLevel) FontWeight.Bold else FontWeight.Normal,
                                        color = if (level == selectedLevel) Color(level.colorHex) else MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = level.subtitle,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            },
                            onClick = {
                                onSelectLevel(level)
                                expandedLevelMenu = false
                            },
                            modifier = Modifier.testTag("level_option_${level.name.lowercase()}")
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatBadge(
    emoji: String,
    value: String,
    bgColor: Color,
    textColor: Color,
    testTag: String
) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(bgColor)
            .padding(horizontal = 7.dp, vertical = 3.dp)
            .testTag(testTag),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = emoji, fontSize = 11.sp)
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = value,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}
