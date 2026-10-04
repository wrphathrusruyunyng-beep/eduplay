package com.example.data.ai

import android.util.Log
import com.example.BuildConfig
import com.example.data.model.FlashcardItem
import com.example.data.model.QuizQuestion
import com.example.data.model.SubjectCategory
import com.example.data.model.EducationLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GeminiAiService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    suspend fun generateSlideOutline(
        topic: String,
        educationLevel: EducationLevel,
        subject: SubjectCategory
    ): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext getFallbackSlideOutline(topic, educationLevel, subject)
        }

        val prompt = """
            คุณเป็นผู้เชี่ยวชาญการออกแบบสื่อการเรียนรู้สำหรับ $subject ระดับชั้น ${educationLevel.title}
            จงสร้าง 'โครงร่างสไลด์และเนื้อหาการสอนแบบกระชับ' สำหรับหัวข้อ: "$topic"
            ให้มีองค์ประกอบดังนี้:
            1. สไลด์หน้าปก (Title & Hook คำถามกระตุ้นความคิด)
            2. วัตถุประสงค์การเรียนรู้ (Learning Objectives)
            3. เนื้อหาหลักแบ่งเป็น 3 สไลด์ย่อย (Slide 1, 2, 3 พร้อม bullet points สรุป)
            4. กิจกรรมอินเทอร์แอ็กทีฟหรือคำถามท้าทายในชั้นเรียน
            5. สรุปบทเรียน & ข้อคิดสำคัญ
            ใช้ภาษาไทยที่เข้าใจง่าย เหมาะสมกับวัย ${educationLevel.title} มีอิโมจิน่าสนใจ
        """.trimIndent()

        try {
            val responseText = executeGeminiRequest(apiKey, prompt)
            if (responseText.isNotBlank()) responseText else getFallbackSlideOutline(topic, educationLevel, subject)
        } catch (e: Exception) {
            Log.e("GeminiAiService", "Error calling Gemini: ${e.message}")
            getFallbackSlideOutline(topic, educationLevel, subject)
        }
    }

    suspend fun generateQuizFromText(
        sourceText: String,
        subject: SubjectCategory = SubjectCategory.SCIENCE,
        level: EducationLevel = EducationLevel.PRIMARY
    ): List<QuizQuestion> = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext getFallbackQuizList(sourceText, subject, level)
        }

        val prompt = """
            จากข้อความเนื้อหาต่อไปนี้ จงสร้างข้อสอบปรนัย 4 ตัวเลือก จำนวน 3 ข้อ พร้อมเฉลยและคำอธิบาย
            เนื้อหา: "$sourceText"
            
            โปรดตอบกลับเป็นรูปแบบ JSON ARRAY เท่านั้น (ไม่มี Markdown code block รอบนอก) โดยมีโครงสร้างดังนี้:
            [
              {
                "question": "คำถาม...",
                "options": ["ตัวเลือก A", "ตัวเลือก B", "ตัวเลือก C", "ตัวเลือก D"],
                "correctIndex": 0,
                "explanation": "คำอธิบายเหตุผลว่าทำไมข้อนี้ถูกต้อง",
                "topic": "ชื่อหัวข้อย่อย"
              }
            ]
        """.trimIndent()

        try {
            val responseText = executeGeminiRequest(apiKey, prompt)
            parseQuizJson(responseText, subject, level).ifEmpty {
                getFallbackQuizList(sourceText, subject, level)
            }
        } catch (e: Exception) {
            Log.e("GeminiAiService", "Error generating quiz: ${e.message}")
            getFallbackQuizList(sourceText, subject, level)
        }
    }

    suspend fun generateVocabCardsFromText(
        articleText: String
    ): List<FlashcardItem> = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext getFallbackVocabCards(articleText)
        }

        val prompt = """
            จากบทความหรือข้อความต่อไปนี้ จงสกัดคำศัพท์สำคัญสองภาษา (ไทยและอังกฤษ) จำนวน 4 คำ พร้อมคำอ่าน สัญลักษณ์อิโมจิ และเกร็ดความรู้สั้นๆ
            ข้อความ: "$articleText"

            โปรดตอบกลับเป็น JSON ARRAY เท่านั้น (ไม่มี Markdown code block):
            [
              {
                "thaiWord": "คำศัพท์ภาษาไทย",
                "englishWord": "English Word",
                "phonetic": "/คำอ่านสัทอักษร/",
                "emoji": "🌟",
                "category": "หมวดคำศัพท์",
                "funFact": "เกร็ดความรู้ 1 ประโยค"
              }
            ]
        """.trimIndent()

        try {
            val responseText = executeGeminiRequest(apiKey, prompt)
            parseVocabJson(responseText).ifEmpty {
                getFallbackVocabCards(articleText)
            }
        } catch (e: Exception) {
            Log.e("GeminiAiService", "Error generating vocab: ${e.message}")
            getFallbackVocabCards(articleText)
        }
    }

    private fun executeGeminiRequest(apiKey: String, prompt: String): String {
        val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"
        val payload = JSONObject().apply {
            val contentsArray = JSONArray().apply {
                val contentObj = JSONObject().apply {
                    val partsArray = JSONArray().apply {
                        val partObj = JSONObject().apply {
                            put("text", prompt)
                        }
                        put(partObj)
                    }
                    put("parts", partsArray)
                }
                put(contentObj)
            }
            put("contents", contentsArray)
        }

        val request = Request.Builder()
            .url(url)
            .post(payload.toString().toRequestBody(jsonMediaType))
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw Exception("HTTP ${response.code}: ${response.message}")
            }
            val body = response.body?.string() ?: ""
            val json = JSONObject(body)
            val candidates = json.optJSONArray("candidates") ?: return ""
            if (candidates.length() == 0) return ""
            val firstCandidate = candidates.getJSONObject(0)
            val content = firstCandidate.optJSONObject("content") ?: return ""
            val parts = content.optJSONArray("parts") ?: return ""
            if (parts.length() == 0) return ""
            return parts.getJSONObject(0).optString("text", "")
        }
    }

    private fun parseQuizJson(
        jsonString: String,
        subject: SubjectCategory,
        level: EducationLevel
    ): List<QuizQuestion> {
        return try {
            val clean = jsonString.trim().removePrefix("```json").removePrefix("```").removeSuffix("```").trim()
            val array = JSONArray(clean)
            val list = mutableListOf<QuizQuestion>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                val question = obj.getString("question")
                val optionsJson = obj.getJSONArray("options")
                val options = mutableListOf<String>()
                for (j in 0 until optionsJson.length()) {
                    options.add(optionsJson.getString(j))
                }
                val correctIndex = obj.optInt("correctIndex", 0)
                val explanation = obj.optString("explanation", "")
                val topic = obj.optString("topic", "แบบทดสอบเนื้อหา")
                list.add(
                    QuizQuestion(
                        id = "ai_q_${System.currentTimeMillis()}_$i",
                        question = question,
                        options = options,
                        correctIndex = correctIndex,
                        explanation = explanation,
                        subject = subject,
                        level = level,
                        topicName = topic
                    )
                )
            }
            list
        } catch (e: Exception) {
            Log.e("GeminiAiService", "Failed to parse quiz json: ${e.message}")
            emptyList()
        }
    }

    private fun parseVocabJson(jsonString: String): List<FlashcardItem> {
        return try {
            val clean = jsonString.trim().removePrefix("```json").removePrefix("```").removeSuffix("```").trim()
            val array = JSONArray(clean)
            val list = mutableListOf<FlashcardItem>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    FlashcardItem(
                        id = "ai_fc_${System.currentTimeMillis()}_$i",
                        thaiWord = obj.optString("thaiWord", "คำศัพท์"),
                        englishWord = obj.optString("englishWord", "Word"),
                        phonetic = obj.optString("phonetic", "/wɜːd/"),
                        emoji = obj.optString("emoji", "💡"),
                        category = obj.optString("category", "คำศัพท์จากบทเรียน"),
                        funFact = obj.optString("funFact", "เป็นคำศัพท์สำคัญที่พบบ่อยในการเรียนรู้")
                    )
                )
            }
            list
        } catch (e: Exception) {
            Log.e("GeminiAiService", "Failed to parse vocab json: ${e.message}")
            emptyList()
        }
    }

    private fun getFallbackSlideOutline(
        topic: String,
        educationLevel: EducationLevel,
        subject: SubjectCategory
    ): String {
        return """
            📊 โครงร่างสไลด์บทเรียน: $topic
            🎯 ระดับชั้น: ${educationLevel.title} | หมวด: ${subject.title}
            
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            📌 สไลด์ 1: หน้าปกและการจุดประกายความคิด
            • หัวข้อหลัก: $topic
            • คำถามชวนคิด: "นักเรียนคิดว่าสิ่งที่เกิดขึ้นใน $topic ส่งผลต่อชีวิตประจำวันของเราอย่างไร?"
            • ภาพสื่อ: แอนิเมชันจำลองสถานการณ์จริง
            
            🎯 สไลด์ 2: วัตถุประสงค์การเรียนรู้
            • เข้าใจความหมายและองค์ประกอบพื้นฐานของ $topic
            • สามารถวิเคราะห์และอธิบายหลักการทำงานได้
            • นำไปประยุกต์ใช้ในการแก้ปัญหาหรือทำโครงงาน
            
            💡 สไลด์ 3: มโนทัศน์หลัก (Core Concept)
            • องค์ประกอบที่ 1: โครงสร้างและนิยามสำคัญ
            • องค์ประกอบที่ 2: กลไกความสัมพันธ์และการทำงาน
            • ตัวอย่างภาพไดอะแกรมประกอบคำอธิบาย
            
            🔬 สไลด์ 4: การลงมือปฏิบัติ / กิจกรรมกลุ่ม
            • ภารกิจย่อย: ทดลองจำลองในห้องแล็บเสมือน
            • บันทึกข้อมูลผลการทดลองและการสังเกต
            
            ✨ สไลด์ 5: สรุปและการประเมินผล
            • สรุป 3 ประเด็นสำคัญที่ต้องจำ
            • มินิควิซทบทวนความเข้าใจ 3 ข้อ
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        """.trimIndent()
    }

    private fun getFallbackQuizList(
        sourceText: String,
        subject: SubjectCategory,
        level: EducationLevel
    ): List<QuizQuestion> {
        val topic = if (sourceText.length > 25) sourceText.take(25) + "..." else sourceText
        return listOf(
            QuizQuestion(
                id = "fb_q1_${System.currentTimeMillis()}",
                question = "จากเนื้อหา '$topic' ข้อความใดต่อไปนี้ถูกต้องและสอดคล้องกับหลักการมากที่สุด?",
                options = listOf(
                    "เป็นกระบวนการที่มีความสัมพันธ์เชิงตรรกะและมีลำดับขั้นตอนชัดเจน",
                    "เป็นปรากฏการณ์ที่เกิดขึ้นโดยไม่มีความสัมพันธ์กับสิ่งแวดล้อม",
                    "ไม่สามารถนำไปประยุกต์ใช้ในการพัฒนาเทคโนโลยีได้",
                    "เกิดขึ้นเฉพาะในห้องปฏิบัติการเท่านั้น"
                ),
                correctIndex = 0,
                explanation = "สาระสำคัญของบทเรียนเน้นที่การจัดลำดับขั้นตอนและหลักการทางวิทยาศาสตร์ที่นำไปใช้ได้จริง",
                subject = subject,
                level = level,
                topicName = "การสรุปความสำคัญ"
            ),
            QuizQuestion(
                id = "fb_q2_${System.currentTimeMillis()}",
                question = "ขั้นตอนสำคัญอันดับแรกในการทำความเข้าใจ '$topic' คืออะไร?",
                options = listOf(
                    "การทำความเข้าใจนิยามและโครงสร้างพื้นฐาน",
                    "การข้ามไปสู่การสรุปผลทันที",
                    "การจำสูตรโดยไม่ต้องเข้าใจที่มา",
                    "การละเว้นการทดสอบความถูกต้อง"
                ),
                correctIndex = 0,
                explanation = "การเข้าใจพื้นฐานและนิยามที่ถูกต้องช่วยให้เชื่อมโยงแนวคิดที่ซับซ้อนขึ้นได้",
                subject = subject,
                level = level,
                topicName = "กระบวนการเรียนรู้"
            )
        )
    }

    private fun getFallbackVocabCards(articleText: String): List<FlashcardItem> {
        return listOf(
            FlashcardItem(
                id = "fb_vc1_${System.currentTimeMillis()}",
                thaiWord = "การเรียนรู้เชิงรุก",
                englishWord = "Active Learning",
                phonetic = "/ˈæk.tɪv ˈlɜː.nɪŋ/",
                emoji = "🚀",
                category = "การศึกษา (Education)",
                funFact = "การเรียนรู้แบบลงมือทำช่วยให้สมองจดจำได้นานขึ้นถึง 75%"
            ),
            FlashcardItem(
                id = "fb_vc2_${System.currentTimeMillis()}",
                thaiWord = "ความคิดสร้างสรรค์",
                englishWord = "Creativity",
                phonetic = "/ˌkriː.eɪˈtɪv.ə.ti/",
                emoji = "🎨",
                category = "ทักษะแห่งอนาคต (Future Skills)",
                funFact = "ความคิดสร้างสรรค์พัฒนาได้จากการสังเกตสิ่งรอบตัวและกล้าทดลองสิ่งใหม่"
            ),
            FlashcardItem(
                id = "fb_vc3_${System.currentTimeMillis()}",
                thaiWord = "การทำงานร่วมกัน",
                englishWord = "Collaboration",
                phonetic = "/kəˌlæb.əˈreɪ.ʃən/",
                emoji = "🤝",
                category = "การทำงานเป็นทีม (Teamwork)",
                funFact = "การทำงานเป็นทีมช่วยรวบรวมมุมมองที่หลากหลายและสร้างผลงานที่ยอดเยี่ยม"
            )
        )
    }
}
