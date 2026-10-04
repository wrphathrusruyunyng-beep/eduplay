package com.example.ui.screens.secondary

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MindMapNode
import com.example.ui.theme.EduIndigo
import com.example.ui.theme.EduViolet

@Composable
fun MindMapScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val nodes = remember {
        mutableStateListOf(
            MindMapNode("root", "🌿 ระบบนิเวศ (Ecosystem)", "ระบบที่กลุ่มสิ่งมีชีวิตอาศัยอยู่ร่วมกันในแหล่งที่อยู่และมีปฏิสัมพันธ์กัน", null, 0xFF4F46E5),
            MindMapNode("n1", "🌞 ผู้ผลิต (Producers)", "พืช สาหร่าย แบคทีเรียสังเคราะห์แสง เปลี่ยนพลังงานแสงอาทิตย์เป็นพลังงานเคมี", "root", 0xFF059669),
            MindMapNode("n2", "🦁 ผู้บริโภค (Consumers)", "สิ่งมีชีวิตที่ไม่สามารถสร้างอาหารเองได้ (สัตว์กินพืช, สัตว์กินเนื้อ, สัตว์กินทั้งพืชและสัตว์)", "root", 0xFFEA580C),
            MindMapNode("n3", "🍄 ผู้ย่อยสลาย (Decomposers)", "เห็ด รา แบคทีเรีย ย่อยสลายซากสิ่งมีชีวิตกลับคืนสู่แร่ธาตุในดิน", "root", 0xFF7C3AED),
            MindMapNode("n4", "🔄 โซ่อาหาร & สายใยอาหาร", "การถ่ายทอดพลังงานตามลำดับขั้นการกิน (Food Chain & Food Web) กฎ 10 เปอร์เซ็นต์", "root", 0xFF0284C7)
        )
    }

    var selectedNode by remember { mutableStateOf<MindMapNode?>(nodes.first()) }
    var isAddingNode by remember { mutableStateOf(false) }
    var newNodeTitle by remember { mutableStateOf("") }
    var newNodeDesc by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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
                modifier = Modifier.testTag("mindmap_back_button")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("กลับ")
            }

            Text(
                text = "ผังมโนทัศน์ (Mind Map) 🧠",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Button(
                onClick = { isAddingNode = !isAddingNode },
                shape = CircleShape,
                modifier = Modifier.testTag("add_mindmap_node_button")
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Node", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("เพิ่มโหนด")
            }
        }

        // Add node form dialog/box
        AnimatedVisibility(visible = isAddingNode) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "เพิ่มประเด็น/กิ่งก้านใหม่", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newNodeTitle,
                        onValueChange = { newNodeTitle = it },
                        label = { Text("หัวข้อ (เช่น สายพันธุ์ใหม่)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newNodeDesc,
                        onValueChange = { newNodeDesc = it },
                        label = { Text("คำอธิบายสรุป") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            if (newNodeTitle.isNotBlank()) {
                                val node = MindMapNode(
                                    id = "n_${System.currentTimeMillis()}",
                                    label = "📌 $newNodeTitle",
                                    description = newNodeDesc.ifBlank { "ประเด็นที่บันทึกเพิ่มเติม" },
                                    parentId = "root",
                                    colorHex = 0xFF2563EB
                                )
                                nodes.add(node)
                                newNodeTitle = ""
                                newNodeDesc = ""
                                isAddingNode = false
                            }
                        },
                        shape = CircleShape
                    ) {
                        Text("บันทึกลงผังมโนทัศน์")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Visual Map Canvas
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                // Root Central Concept
                val rootNode = nodes.find { it.id == "root" } ?: nodes.first()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(rootNode.colorHex))
                        .clickable { selectedNode = rootNode }
                        .padding(18.dp)
                        .testTag("node_root"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = rootNode.label,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Connecting branch lines
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(20.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(Color(0xFFCBD5E1))
                )

                // Sub nodes in grid-like branch cards
                val children = nodes.filter { it.parentId == "root" }
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    children.forEach { child ->
                        val isSelected = selectedNode?.id == child.id
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) Color(child.colorHex).copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant)
                                .border(
                                    width = if (isSelected) 2.dp else 0.dp,
                                    color = if (isSelected) Color(child.colorHex) else Color.Transparent,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .clickable { selectedNode = child }
                                .padding(14.dp)
                                .testTag("node_${child.id}")
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(Color(child.colorHex))
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = child.label,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color(child.colorHex) else MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Selected Node Concept Detail Card
        selectedNode?.let { node ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Hub, contentDescription = "Concept", tint = Color(node.colorHex))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = node.label,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(node.colorHex)
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = node.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 22.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        }
    }
}
