package com.example.ui.screens.secondary

import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EduAmber
import com.example.ui.theme.EduEmerald
import com.example.ui.theme.EduIndigo
import com.example.ui.theme.EduRose

@Composable
fun VirtualLabScreen(
    graphA: Float,
    graphB: Float,
    graphC: Float,
    voltage: Float,
    resistance: Float,
    isSwitchClosed: Boolean,
    onUpdateGraphParams: (a: Float, b: Float, c: Float) -> Unit,
    onUpdateCircuitParams: (v: Float, r: Float, closed: Boolean) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedLabTab by remember { mutableIntStateOf(0) } // 0 = Parabola Grapher, 1 = Electric Circuit

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledTonalButton(
                onClick = onBack,
                modifier = Modifier.testTag("virtual_lab_back_button")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("กลับ")
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "ห้องทดลองเสมือน (Virtual Lab) 🔬",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Lab Selector Tab
        TabRow(
            selectedTabIndex = selectedLabTab,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.clip(RoundedCornerShape(14.dp))
        ) {
            Tab(
                selected = selectedLabTab == 0,
                onClick = { selectedLabTab = 0 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ShowChart, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("กราฟพาราโบลา", fontWeight = FontWeight.Bold)
                    }
                },
                modifier = Modifier.testTag("tab_parabola_grapher")
            )
            Tab(
                selected = selectedLabTab == 1,
                onClick = { selectedLabTab = 1 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ElectricBolt, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("วงจรไฟฟ้า (กฎของโอห์ม)", fontWeight = FontWeight.Bold)
                    }
                },
                modifier = Modifier.testTag("tab_circuit_simulator")
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedLabTab == 0) {
            // Parabola Graph Simulator
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "📐 ตัวจำลองกราฟพาราโบลา: y = ax² + bx + c",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = EduIndigo
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Real-time Canvas Graph
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF0F172A))
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val centerX = size.width / 2
                            val centerY = size.height / 2
                            val scale = 25f // pixels per unit

                            // Draw axes
                            drawLine(
                                color = Color(0xFF334155),
                                start = Offset(0f, centerY),
                                end = Offset(size.width, centerY),
                                strokeWidth = 2f
                            )
                            drawLine(
                                color = Color(0xFF334155),
                                start = Offset(centerX, 0f),
                                end = Offset(centerX, size.height),
                                strokeWidth = 2f
                            )

                            // Plot y = ax^2 + bx + c
                            val path = Path()
                            var isFirst = true

                            val minX = -10f
                            val maxX = 10f
                            val step = 0.2f

                            var x = minX
                            while (x <= maxX) {
                                val y = graphA * (x * x) + graphB * x + graphC
                                val px = centerX + (x * scale)
                                val py = centerY - (y * scale)

                                if (isFirst) {
                                    path.moveTo(px, py)
                                    isFirst = false
                                } else {
                                    path.lineTo(px, py)
                                }
                                x += step
                            }

                            drawPath(
                                path = path,
                                color = Color(0xFF38BDF8),
                                style = Stroke(width = 3.5f)
                            )

                            // Vertex point: h = -b / (2a)
                            if (graphA != 0f) {
                                val h = -graphB / (2 * graphA)
                                val k = graphA * (h * h) + graphB * h + graphC
                                val vx = centerX + (h * scale)
                                val vy = centerY - (k * scale)
                                drawCircle(
                                    color = Color(0xFFF43F5E),
                                    radius = 7f,
                                    center = Offset(vx, vy)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Analysis summary
                    val isUpward = graphA > 0
                    val h = if (graphA != 0f) -graphB / (2 * graphA) else 0f
                    val k = graphA * (h * h) + graphB * h + graphC

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFEEF2FF))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "สมการ: y = ${"%.1f".format(graphA)}x² + ${"%.1f".format(graphB)}x + ${"%.1f".format(graphC)}\n" +
                                    "ลักษณะ: ${if (isUpward) "พาราโบลาหงาย 🟢" else "พาราโบลาคว่ำ 🔴"} | จุดยอด: (h, k) = (${"%.2f".format(h)}, ${"%.2f".format(k)})",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = EduIndigo
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sliders
                    Text(text = "สัมประสิทธิ์ a: ${"%.1f".format(graphA)} (กำหนดความกว้าง/หงายคว่ำ)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Slider(
                        value = graphA,
                        onValueChange = { onUpdateGraphParams(it, graphB, graphC) },
                        valueRange = -3f..3f,
                        modifier = Modifier.testTag("slider_graph_a")
                    )

                    Text(text = "สัมประสิทธิ์ b: ${"%.1f".format(graphB)} (กำหนดการเลื่อนแกนสมมาตร)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Slider(
                        value = graphB,
                        onValueChange = { onUpdateGraphParams(graphA, it, graphC) },
                        valueRange = -5f..5f,
                        modifier = Modifier.testTag("slider_graph_b")
                    )

                    Text(text = "ค่าคงที่ c: ${"%.1f".format(graphC)} (จุดตัดแกน Y)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Slider(
                        value = graphC,
                        onValueChange = { onUpdateGraphParams(graphA, graphB, it) },
                        valueRange = -5f..5f,
                        modifier = Modifier.testTag("slider_graph_c")
                    )
                }
            }
        } else {
            // Electric Circuit Simulator (Ohm's Law: V = I * R => I = V / R)
            val currentAmps = if (isSwitchClosed && resistance > 0) voltage / resistance else 0f
            val bulbAlpha = if (isSwitchClosed) (currentAmps / 4f).coerceIn(0.2f, 1f) else 0.1f

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "⚡ จำลองวงจรไฟฟ้าตามกฎของโอห์ม (V = I × R)",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = EduAmber
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Circuit schematic board
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF1E293B)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            // Light bulb emoji glowing
                            Text(
                                text = if (isSwitchClosed) "💡" else "⚪",
                                fontSize = 56.sp,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(if (isSwitchClosed) Color(0xFFFDE047).copy(alpha = bulbAlpha * 0.4f) else Color.Transparent)
                                    .padding(10.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = if (isSwitchClosed) "ความสว่างหลอดไฟ: ${(bulbAlpha * 100).toInt()}%" else "สวิตช์เปิดอยู่ (วงจรเปิด - ไฟดับ)",
                                fontSize = 12.sp,
                                color = if (isSwitchClosed) Color(0xFFFDE047) else Color.LightGray,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Readout values
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        CircuitStat(label = "แรงดัน (V)", value = "${"%.1f".format(voltage)} V", color = EduIndigo)
                        CircuitStat(label = "ความต้านทาน (R)", value = "${"%.1f".format(resistance)} Ω", color = EduEmerald)
                        CircuitStat(label = "กระแสไฟฟ้า (I)", value = "${"%.2f".format(currentAmps)} A", color = EduAmber)
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "สวิตช์ไฟฟ้า (Switch): ${if (isSwitchClosed) "ปิดสวิตช์ (ON)" else "เปิดสวิตช์ (OFF)"}",
                            fontWeight = FontWeight.Bold
                        )
                        Switch(
                            checked = isSwitchClosed,
                            onCheckedChange = { onUpdateCircuitParams(voltage, resistance, it) },
                            modifier = Modifier.testTag("circuit_switch_toggle")
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "ปรับแรงดันแบตเตอรี่ (V): ${"%.1f".format(voltage)} โวลต์", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Slider(
                        value = voltage,
                        onValueChange = { onUpdateCircuitParams(it, resistance, isSwitchClosed) },
                        valueRange = 1f..24f,
                        modifier = Modifier.testTag("slider_circuit_voltage")
                    )

                    Text(text = "ปรับความต้านทานตัวต้านทาน (R): ${"%.1f".format(resistance)} โอห์ม", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Slider(
                        value = resistance,
                        onValueChange = { onUpdateCircuitParams(voltage, it, isSwitchClosed) },
                        valueRange = 1f..20f,
                        modifier = Modifier.testTag("slider_circuit_resistance")
                    )
                }
            }
        }
    }
}

@Composable
fun CircuitStat(label: String, value: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(text = label, fontSize = 11.sp, color = color)
        Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = color)
    }
}
