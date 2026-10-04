package com.example.ui.screens.kindergarten

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Save
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EduEmerald
import com.example.ui.theme.EduIndigo

data class DrawnPath(
    val path: Path,
    val color: Color,
    val strokeWidth: Float
)

data class StickerStamp(
    val emoji: String,
    val position: Offset
)

@Composable
fun ColoringCanvasScreen(
    onSaveArtwork: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val paths = remember { mutableStateListOf<DrawnPath>() }
    val stickers = remember { mutableStateListOf<StickerStamp>() }

    var currentColor by remember { mutableStateOf(Color(0xFFE11D48)) }
    var currentStrokeWidth by remember { mutableStateOf(16f) }
    var selectedSticker by remember { mutableStateOf<String?>(null) }
    var isSavedNotification by remember { mutableStateOf(false) }

    val palette = listOf(
        Color(0xFFE11D48), // Red
        Color(0xFFF97316), // Orange
        Color(0xFFFACC15), // Yellow
        Color(0xFF10B981), // Green
        Color(0xFF06B6D4), // Cyan
        Color(0xFF3B82F6), // Blue
        Color(0xFF8B5CF6), // Purple
        Color(0xFFEC4899), // Pink
        Color(0xFF0F172A), // Dark
        Color.White        // Eraser
    )

    val stickerOptions = listOf("⭐", "🌸", "🚀", "🐶", "🌈", "🍎")

    var currentPath by remember { mutableStateOf<Path?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilledTonalButton(
                onClick = onBack,
                modifier = Modifier.testTag("coloring_back_button")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("กลับ")
            }

            Text(
                text = "กระดานวาด & ระบายสี 🎨",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = {
                        paths.clear()
                        stickers.clear()
                        isSavedNotification = false
                    },
                    shape = CircleShape,
                    modifier = Modifier.testTag("clear_canvas_button")
                ) {
                    Icon(Icons.Default.CleaningServices, contentDescription = "Clear", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("ล้าง")
                }

                Button(
                    onClick = {
                        onSaveArtwork("ผลงานศิลปะระบายสี (เส้นวาด ${paths.size} เส้น, สติ๊กเกอร์ ${stickers.size} ชิ้น)")
                        isSavedNotification = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EduEmerald),
                    shape = CircleShape,
                    modifier = Modifier.testTag("save_artwork_button")
                ) {
                    Icon(Icons.Default.Save, contentDescription = "Save", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("บันทึก")
                }
            }
        }

        if (isSavedNotification) {
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD1FAE5)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "✅ บันทึกผลงานลงในคลังสื่อเรียบร้อยแล้ว (+50 XP)!",
                    color = Color(0xFF065F46),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Drawing Surface
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(selectedSticker, currentColor, currentStrokeWidth) {
                            if (selectedSticker != null) {
                                awaitPointerEventScope {
                                    while (true) {
                                        val event = awaitPointerEvent()
                                        val change = event.changes.firstOrNull() ?: continue
                                        if (change.pressed) {
                                            stickers.add(StickerStamp(selectedSticker!!, change.position))
                                            change.consume()
                                        }
                                    }
                                }
                            } else {
                                detectDragGestures(
                                    onDragStart = { offset ->
                                        val p = Path().apply { moveTo(offset.x, offset.y) }
                                        currentPath = p
                                        paths.add(DrawnPath(p, currentColor, currentStrokeWidth))
                                    },
                                    onDrag = { change, _ ->
                                        currentPath?.lineTo(change.position.x, change.position.y)
                                        change.consume()
                                    },
                                    onDragEnd = {
                                        currentPath = null
                                    }
                                )
                            }
                        }
                        .testTag("drawing_canvas")
                ) {
                    // Draw outline template guide (friendly apple & star)
                    drawCircle(
                        color = Color(0xFFF1F5F9),
                        radius = size.minDimension * 0.35f,
                        center = center
                    )

                    // Draw all user paths
                    for (dp in paths) {
                        drawPath(
                            path = dp.path,
                            color = dp.color,
                            style = Stroke(
                                width = dp.strokeWidth,
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }
                }

                // Render stickers
                stickers.forEach { sticker ->
                    Text(
                        text = sticker.emoji,
                        fontSize = 32.sp,
                        modifier = Modifier.padding(
                            start = (sticker.position.x - 30).coerceAtLeast(0f).dp / 2.7f,
                            top = (sticker.position.y - 30).coerceAtLeast(0f).dp / 2.7f
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Color Palette Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            palette.forEach { color ->
                val isSelected = currentColor == color && selectedSticker == null
                Box(
                    modifier = Modifier
                        .size(if (isSelected) 34.dp else 28.dp)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = if (isSelected) 3.dp else 1.dp,
                            color = if (isSelected) EduIndigo else Color.LightGray,
                            shape = CircleShape
                        )
                        .clickable {
                            currentColor = color
                            selectedSticker = null
                        }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Sticker stamps & brush size row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Sticker bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = "สติกเกอร์:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                stickerOptions.forEach { emoji ->
                    val isSelected = selectedSticker == emoji
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) Color(0xFFEEF2FF) else Color.Transparent)
                            .border(
                                width = if (isSelected) 2.dp else 0.dp,
                                color = if (isSelected) EduIndigo else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                selectedSticker = if (isSelected) null else emoji
                            }
                            .padding(4.dp)
                    ) {
                        Text(text = emoji, fontSize = 20.sp)
                    }
                }
            }

            // Brush sizes
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(8f to "เล็ก", 16f to "กลาง", 28f to "ใหญ่").forEach { (width, label) ->
                    val isSelected = currentStrokeWidth == width && selectedSticker == null
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) Color(0xFFEEF2FF) else Color(0xFFF1F5F9))
                            .clickable {
                                currentStrokeWidth = width
                                selectedSticker = null
                            }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = label,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) EduIndigo else Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}
