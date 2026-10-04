package com.example.data.model

object SampleEduData {

    val flashcards = listOf(
        FlashcardItem(
            id = "fc_1",
            thaiWord = "ช้าง",
            englishWord = "Elephant",
            phonetic = "/ˈel.ɪ.fənt/",
            emoji = "🐘",
            category = "สัตว์ (Animals)",
            funFact = "ช้างเป็นสัตว์บกที่มีขนาดใหญ่ที่สุดในโลก และจำเก่งมาก!"
        ),
        FlashcardItem(
            id = "fc_2",
            thaiWord = "ผีเสื้อ",
            englishWord = "Butterfly",
            phonetic = "/ˈbʌt.ə.flaɪ/",
            emoji = "🦋",
            category = "สัตว์ (Animals)",
            funFact = "ผีเสื้อรับรสชาติของอาหารได้ด้วยปลายเท้าของมันนะ!"
        ),
        FlashcardItem(
            id = "fc_3",
            thaiWord = "สับปะรด",
            englishWord = "Pineapple",
            phonetic = "/ˈpaɪnˌæp.əl/",
            emoji = "🍍",
            category = "ผลไม้ (Fruits)",
            funFact = "สับปะรดอุดมไปด้วยวิตามินซี ช่วยให้ร่างกายแข็งแรง"
        ),
        FlashcardItem(
            id = "fc_4",
            thaiWord = "แตงโม",
            englishWord = "Watermelon",
            phonetic = "/ˈwɔː.təˌmel.ən/",
            emoji = "🍉",
            category = "ผลไม้ (Fruits)",
            funFact = "แตงโมมีน้ำประกอบอยู่ถึง 92% กินแล้วสดชื่นสุดๆ"
        ),
        FlashcardItem(
            id = "fc_5",
            thaiWord = "สีรุ้ง",
            englishWord = "Rainbow",
            phonetic = "/ˈreɪn.bəʊ/",
            emoji = "🌈",
            category = "ธรรมชาติและสี (Nature & Colors)",
            funFact = "รุ้งกินน้ำเกิดจากแสงแดดสะท้อนและหักเหผ่านละอองน้ำฝน"
        ),
        FlashcardItem(
            id = "fc_6",
            thaiWord = "ยานอวกาศ",
            englishWord = "Spaceship",
            phonetic = "/ˈspeɪs.ʃɪp/",
            emoji = "🚀",
            category = "ยานพาหนะ (Vehicles)",
            funFact = "จรวดต้องใช้ความเร็วสูงมากเพื่อหลุดพ้นจากแรงดึงดูดของโลก"
        )
    )

    val storyPages = listOf(
        InteractiveStoryPage(
            pageNumber = 1,
            title = "ตอนที่ 1: การท้าประลองในป่าใหญ่",
            textThai = "กาลครั้งหนึ่ง ณ ชายป่าอันเงียบสงบ กระต่ายน้อยจอมไววิ่งกระโดดไปมา แล้วพูดท้าทายคุณเต่าผู้เดินต้วมเตี้ยมว่า 'เจ้าเต่าเอ๋ย ลองมาวิ่งแข่งกับข้าดูไหมล่ะ?'",
            textEng = "Once upon a time in a peaceful forest, a fast rabbit boasted to a slow turtle: 'Hey turtle, dare to race me?'",
            sceneEmoji = "🌲",
            characters = listOf(
                StoryCharacter("c1", "กระต่ายแสนไว", "🐰", "กระโดด ดึ๋ง ดึ๋ง!", "กระโดดโลดเต้นอย่างมั่นใจ"),
                StoryCharacter("c2", "คุณเต่าใจเย็น", "🐢", "ก้าว ช้าๆ แต่มั่นคง!", "พยักหน้ายิ้มรับคำท้า")
            ),
            interactivePrompt = "แตะที่ตัวกระต่ายหรือคุณเต่าเพื่อฟังเสียงและดูท่าทาง!"
        ),
        InteractiveStoryPage(
            pageNumber = 2,
            title = "ตอนที่ 2: ความประมาทใต้ต้นไม้ใหญ่",
            textThai = "เมื่อการแข่งขันเริ่มขึ้น กระต่ายวิ่งนำลิ่วจนมองไม่เห็นเต่า 'ฮ่าๆ เต่ายังอยู่ไกลลิบ ขอนอนงีบเอาแรงใต้ร่มไม้สักพักแล้วกัน' กระต่ายหลับปุ๋ยไปอย่างสบายใจ",
            textEng = "The race began and the rabbit ran far ahead. 'The turtle is far behind, let me take a quick nap under the tree,' he snoozed.",
            sceneEmoji = "🌳",
            characters = listOf(
                StoryCharacter("c1", "กระต่ายขี้เซา", "😴", "ฟี้... ฟี้... ครอกฟี้!", "นอนหลับฝันหวานใต้ต้นไม้"),
                StoryCharacter("c2", "คุณเต่ามุ่งมั่น", "🐢", "ตึ้ก... ตึ้ก... ก้าวต่อไป!", "เดินก้าวอย่างสม่ำเสมอไม่หยุดพัก")
            ),
            interactivePrompt = "แตะที่ต้นไม้เพื่อปลุกกระต่าย หรือแตะที่เต่าเพื่อส่งกำลังใจ!"
        ),
        InteractiveStoryPage(
            pageNumber = 3,
            title = "ตอนที่ 3: เส้นชัยแห่งความเพียรพยายาม",
            textThai = "เมื่อกระต่ายตื่นขึ้นมา ก็พบว่าคุณเต่าได้เดินข้ามเส้นชัยไปแล้วอย่างสง่างาม! สัตว์ทั้งป่าต่างส่งเสียงเฮชื่นชม กระต่ายจึงเรียนรู้ว่าความพยายามและความสม่ำเสมอชนะความประมาทเสมอ",
            textEng = "When the rabbit woke up, the turtle had already crossed the finish line! Steady perseverance always triumphs over arrogance.",
            sceneEmoji = "🏁",
            characters = listOf(
                StoryCharacter("c2", "คุณเต่าผู้ชนะ", "🏆", "ไชโย! ทำสำเร็จแล้ว!", "ชูถ้วยรางวัลด้วยรอยยิ้ม"),
                StoryCharacter("c3", "นกน้อยผู้ตัดสิน", "🐦", "จิ๊บๆ ยินดีด้วยนะ!", "บินโปรยดอกไม้ยินดี")
            ),
            interactivePrompt = "แตะที่ถ้วยรางวัลเพื่อจุดพลุฉลองชัยชนะ!"
        )
    )

    val dragDropPairs = listOf(
        DragDropPair("dd1", "สุนัข (Dog)", "dog", "🐶", "ส่งเสียง: บ็อก บ็อก! เป็นเพื่อนที่ซื่อสัตย์"),
        DragDropPair("dd2", "แมวน้อย (Cat)", "cat", "🐱", "ส่งเสียง: เหมียว เหมียว! ชอบจับหนูและคลอเคลีย"),
        DragDropPair("dd3", "กบกระโดด (Frog)", "frog", "🐸", "ส่งเสียง: อ๊บ อ๊บ! ว่ายน้ำได้และกระโดดเก่ง"),
        DragDropPair("dd4", "นกขมิ้น (Bird)", "bird", "🐦", "ส่งเสียง: จิ๊บ จิ๊บ! บินอยู่บนท้องฟ้ากว้างใหญ่"),
        DragDropPair("dd5", "ลิงจ๋อ (Monkey)", "monkey", "🐵", "ส่งเสียง: เจี๊ยก เจี๊ยก! ชอบกินกล้วยและห้อยโหน")
    )

    val butterflyLifeCycle = listOf(
        LifeCycleStage(1, "ระยะที่ 1: ไข่ (Egg)", "🥚", "แม่ผีเสื้อวางไข่ฟองจิ๋วบนใบไม้ที่สดชื่น", "ใช้เวลาประมาณ 3-5 วันก่อนจะฟักออกมา"),
        LifeCycleStage(2, "ระยะที่ 2: ตัวหนอน (Caterpillar)", "🐛", "หนอนกินใบไม้อย่างเอร็ดอร่อยเพื่อสะสมพลังงานและลอกคราบ", "ขนาดตัวจะโตขึ้นกว่าเดิมถึง 100 เท่า!"),
        LifeCycleStage(3, "ระยะที่ 3: ดักแด้ (Chrysalis)", "🌿", "หนอนชักใยหุ้มตัวเพื่อเริ่มกระบวนการแปรสัณฐานภายใน", "ร่างกายเดิมจะสลายตัวและสร้างปีกและอวัยวะใหม่"),
        LifeCycleStage(4, "ระยะที่ 4: ผีเสื้อตัวเต็มวัย (Butterfly)", "🦋", "กางปีกสีสันสดใส บินดูดน้ำหวานจากดอกไม้เพื่อผสมเกสร", "พร้อมสืบพันธุ์และเริ่มต้นวงจรชีวิตรอบใหม่")
    )

    val frogLifeCycle = listOf(
        LifeCycleStage(1, "ไข่กบ (Frog Eggs)", "🍳", "วางเป็นกลุ่มวุ้นใสในแหล่งน้ำเพื่อป้องกันสัตว์อื่นกิน", "แม่กบวางไข่ได้มากถึงหลักพันฟอง"),
        LifeCycleStage(2, "ลูกอ๊อด (Tadpole)", "🐟", "ว่ายน้ำและหายใจด้วยเหงือกเหมือนปลา", "มีหางยาวและกินสาหร่ายในน้ำ"),
        LifeCycleStage(3, "ลูกอ๊อดมีขา (Froglet)", "🦎", "เริ่มงอกขาหลังและขาหน้า หางเริ่มหดสั้นลง", "เริ่มพัฒนาปอดเพื่อขึ้นมาหายใจบนบก"),
        LifeCycleStage(4, "กบตัวเต็มวัย (Adult Frog)", "🐸", "ไม่มีหาง หายใจด้วยปอดและผิวหนัง กระโดดได้ไกล", "กลับสู่บึงน้ำเพื่อวางไข่อีกครั้ง")
    )

    val readingPassages = listOf(
        "กระต่ายน้อยกระโดดเล่นในสวนดอกไม้สีสดใสอย่างมีความสุข",
        "ท้องฟ้าวันนี้แจ่มใส นกน้อยร้องเพลงขับขานต้อนรับรุ่งอรุณ",
        "ความรู้เปรียบเสมือนแสงประทีปส่องทางสู่ความสำเร็จในชีวิต",
        "The gentle morning breeze whispered through the green forest trees."
    )

    val quizQuestions = listOf(
        QuizQuestion(
            id = "q1",
            question = "กระบวนการสังเคราะห์ด้วยแสงของพืช ต้องใช้สารใดเป็นวัตถุดิบหลัก?",
            options = listOf("ก๊าซคาร์บอนไดออกไซด์ และ น้ำ", "ก๊าซออกซิเจน และ ไฮโดรเจน", "ปุ๋ยเคมี และ น้ำตาลกลูโคส", "ก๊าซไนโตรเจน และ ดิน"),
            correctIndex = 0,
            explanation = "พืชใช้คาร์บอนไดออกไซด์จากอากาศ น้ำจากดิน ร่วมกับแสงแดดและคลอโรฟิลล์ ได้ผลิตภัณฑ์เป็นน้ำตาลกลูโคสและออกซิเจน",
            subject = SubjectCategory.SCIENCE,
            level = EducationLevel.PRIMARY,
            topicName = "การสังเคราะห์ด้วยแสง"
        ),
        QuizQuestion(
            id = "q2",
            question = "ถ้าต้องการหาพื้นที่ของรูปสามเหลี่ยมที่มีฐานยาว 10 ซม. และสูง 6 ซม. จะได้พื้นที่เท่าใด?",
            options = listOf("30 ตารางเซนติเมตร", "60 ตารางเซนติเมตร", "16 ตารางเซนติเมตร", "45 ตารางเซนติเมตร"),
            correctIndex = 0,
            explanation = "สูตรพื้นที่สามเหลี่ยม = 1/2 × ฐาน × สูง = 1/2 × 10 × 6 = 30 ตร.ซม.",
            subject = SubjectCategory.MATHEMATICS,
            level = EducationLevel.PRIMARY,
            topicName = "เรขาคณิตและการหาพื้นที่"
        ),
        QuizQuestion(
            id = "q3",
            question = "คำในข้อใดสะกดด้วย 'มาตราตัวสะกดแม่กน' ทุกคำ?",
            options = listOf("ปลาวาฬ, ดอกบัว, โรงเรียน", "กานดา, บุญคุณ, บันได", "กตัญญู, เมฆหมอก, ความรัก", "วิหค, กาลเวลา, สายรุ้ง"),
            correctIndex = 1,
            explanation = "แม่กน มีตัวสะกด น, ณ, ญ, ร, ล, ฬ เช่น กาน (น), ดา, บุญ (ญ), คุณ (ณ), บัน (น), ได",
            subject = SubjectCategory.LANGUAGES,
            level = EducationLevel.PRIMARY,
            topicName = "หลักภาษาไทย มาตราตัวสะกด"
        ),
        QuizQuestion(
            id = "q4",
            question = "ตามกฎของโอห์ม (Ohm's Law) ถ้าความต่างศักย์ไฟฟ้า V = 12 โวลต์ และความต้านทาน R = 4 โอห์ม กระแสไฟฟ้า I จะมีค่าเท่าใด?",
            options = listOf("3 แอมแปร์", "48 แอมแปร์", "8 แอมแปร์", "0.33 แอมแปร์"),
            correctIndex = 0,
            explanation = "จากสมการ V = I × R ดังนั้น I = V / R = 12 / 4 = 3 แอมแปร์ (A)",
            subject = SubjectCategory.SCIENCE,
            level = EducationLevel.SECONDARY,
            topicName = "ไฟฟ้าและกฎของโอห์ม"
        ),
        QuizQuestion(
            id = "q5",
            question = "กราฟพาราโบลา y = ax² + bx + c จะมีลักษณะ 'หงาย' เมื่อใด?",
            options = listOf("เมื่อ a > 0", "เมื่อ a < 0", "เมื่อ b > 0", "เมื่อ c = 0"),
            correctIndex = 0,
            explanation = "ถ้าสัมประสิทธิ์ a เป็นบวก (a > 0) พาราโบลาจะหงายและมีจุดยอดเป็นจุดต่ำสุด",
            subject = SubjectCategory.MATHEMATICS,
            level = EducationLevel.SECONDARY,
            topicName = "ฟังก์ชันกำลังสองและพาราโบลา"
        ),
        QuizQuestion(
            id = "q6",
            question = "โปรโตคอล HTTP Method ใดที่ใช้สำหรับสร้างทรัพยากรใหม่ (Create) ในสถาปัตยกรรม REST API?",
            options = listOf("POST", "GET", "PUT", "DELETE"),
            correctIndex = 0,
            explanation = "POST ใช้สำหรับ Create ทรัพยากรใหม่, GET ใช้สำหรับ Read, PUT/PATCH สำหรับ Update, DELETE สำหรับลบ",
            subject = SubjectCategory.TECH_VOCATIONAL,
            level = EducationLevel.VOCATIONAL,
            topicName = "สถาปัตยกรรมเว็บ & API"
        )
    )

    val technicalGuides = listOf(
        TechnicalGuide(
            id = "tg_1",
            title = "การต่อวงจรมอเตอร์ควบคุมเดินหน้า-ถอยหลัง (Forward-Reverse Motor Control)",
            field = "สาขาวิชาช่างไฟฟ้ากำลัง / แมคคาทรอนิกส์",
            toolsNeeded = listOf("Magnetic Contactor 2 ตัว", "Thermal Overload Relay", "Push Button NO/NC", "มัลติมิเตอร์วัดไฟ"),
            steps = listOf(
                TechnicalGuideStep(
                    stepNumber = 1,
                    title = "ตรวจสอบความปลอดภัยและตัดแหล่งจ่ายไฟ (Lockout/Tagout)",
                    instructions = "สับเบรกเกอร์หลักลง ตรวจสอบด้วยไขควงเช็กไฟหรือมิเตอร์วัดแรงดันว่าไม่มีกระแสไฟฟ้าค้างในระบบก่อนเริ่มต่อสายเด็ดขาด",
                    safetyTip = "⚠️ อันตรายจากไฟฟ้าแรงสูง สวมถุงมือและรองเท้านิรภัยฉนวนไฟฟ้าทุกครั้ง",
                    diagramSymbol = "⚡ [MAIN POWER OFF]"
                ),
                TechnicalGuideStep(
                    stepNumber = 2,
                    title = "การต่อวงจรกำลัง (Power Circuit) สลับเฟส",
                    instructions = "ต่อสาย Line L1, L2, L3 เข้าแมกเนติกตัวที่ 1 (KM1) และต่อขนานเข้าแมกเนติกตัวที่ 2 (KM2) โดยสลับสาย 2 เฟส (เช่น L1 กับ L3) เพื่อให้สนามแม่เหล็กหมุนกลับทิศทาง",
                    codeSnippet = "// Power Wiring Rule:\nKM1: L1->U, L2->V, L3->W (Forward)\nKM2: L3->U, L2->V, L1->W (Reverse)",
                    diagramSymbol = "L1 L2 L3 ──[KM1]──► Motor (FWD)\n      └────[KM2]──► Motor (REV [สลับเฟส])"
                ),
                TechnicalGuideStep(
                    stepNumber = 3,
                    title = "การต่อระบบอินเตอร์ล็อกไฟฟ้า (Electrical Interlock)",
                    instructions = "นำหน้าสัมผัส NC ของ KM1 มาตัดวงจรคอยล์ของ KM2 และนำ NC ของ KM2 มาตัดคอยล์ของ KM1 เพื่อป้องกันไม่ให้แมกเนติกทั้งสองทำงานพร้อมกัน ซึ่งจะทำให้เกิดการลัดวงจรระหว่างเฟส",
                    safetyTip = "สำคัญมาก: ต้องมีทั้ง Electrical Interlock และ Mechanical Interlock เพื่อความปลอดภัยสูงสุด",
                    diagramSymbol = "[PB_REV] ───[/KM1 NC]─── (KM2 Coil)"
                )
            )
        ),
        TechnicalGuide(
            id = "tg_2",
            title = "การพัฒนา REST API ด้วย Kotlin & Ktor พร้อม SQLite",
            field = "สาขาวิชาเทคโนโลยีสารสนเทศ / วิทยาการคอมพิวเตอร์",
            toolsNeeded = listOf("Android Studio / IntelliJ", "Kotlin SDK 2.x", "Postman / Curl", "Git"),
            steps = listOf(
                TechnicalGuideStep(
                    stepNumber = 1,
                    title = "กำหนด Routing และ Endpoints",
                    instructions = "สร้าง Route สำหรับ Resource /api/lessons รองรับ GET และ POST สำหรับดึงและบันทึกข้อมูล",
                    codeSnippet = "routing {\n    route(\"/api/lessons\") {\n        get {\n            val lessons = lessonService.getAll()\n            call.respond(HttpStatusCode.OK, lessons)\n        }\n        post {\n            val newLesson = call.receive<LessonCreateDto>()\n            val created = lessonService.create(newLesson)\n            call.respond(HttpStatusCode.Created, created)\n        }\n    }\n}"
                ),
                TechnicalGuideStep(
                    stepNumber = 2,
                    title = "เพิ่ม Content Negotiation & JSON Serialization",
                    instructions = "ติดตั้งปลั๊กอิน ContentNegotiation และใช้งาน kotlinx.serialization เพื่อแปลงโมเดลเป็น JSON อัตโนมัติ",
                    codeSnippet = "install(ContentNegotiation) {\n    json(Json {\n        prettyPrint = true\n        isLenient = true\n        ignoreUnknownKeys = true\n    })\n}"
                )
            )
        )
    )

    val initialSubmissions = listOf(
        StudentSubmission(
            id = "sub_1",
            studentName = "นายธนกร แสนสุข (ปวส.2 อิเล็กทรอนิกส์)",
            projectTitle = "ระบบ Smart Farm รดน้ำแปลงผักอัตโนมัติด้วยเซนเซอร์ความชื้นและ IoT",
            submittedAt = "เมื่อวานนี้ 15:30 น.",
            rubricScores = listOf(
                RubricScore("1. ความสมบูรณ์ของวงจรและการทำงานจริง", 25, 24, "วงจรตัดต่อแม่นยำ เซนเซอร์ตอบสนองเร็ว"),
                RubricScore("2. ความปลอดภัยและการจัดสายมาตรฐานช่าง", 25, 23, "เก็บสายร้อยท่อเรียบร้อย มีฟิวส์ตัดไฟ"),
                RubricScore("3. โค้ดโปรแกรมและการประมวลผล", 25, 22, "เขียนอ่านง่าย มี comment ชัดเจน"),
                RubricScore("4. รายงานเอกสารและสรุปผล", 25, 23, "รูปเล่มสมบูรณ์ มีกราฟความชื้นเปรียบเทียบ")
            ),
            teacherComment = "ผลงานยอดเยี่ยม มีการนำไปติดตั้งใช้งานจริงในแปลงเกษตรของวิทยาลัยได้ดีมาก ขอให้พัฒนาเรื่อง Dashboard แสดงผลบนมือถือเพิ่มเติมในเฟสต่อไป",
            status = "ตรวจแล้ว (92/100 A)"
        ),
        StudentSubmission(
            id = "sub_2",
            studentName = "นางสาวพิชชาพร ก้องกิตติ (ปวช.3 เทคโนโลยีสารสนเทศ)",
            projectTitle = "เว็บแอปพลิเคชันคัดแยกขยะรีไซเคิลด้วย Image Classification AI",
            submittedAt = "วันนี้ 09:15 น.",
            rubricScores = listOf(
                RubricScore("1. ความสมบูรณ์ของระบบและการทำงานจริง", 25, 23, "โมเดลตรวจจับขวดพลาสติกและกระป๋องได้ดี"),
                RubricScore("2. การออกแบบ UI/UX สวยงามใช้งานง่าย", 25, 24, "หน้าตาสะอาดตา มีเสียงแจ้งเตือนชัดเจน"),
                RubricScore("3. ความปลอดภัยและโครงสร้างระบบ", 25, 22, "มีระบบ Token ป้องกันการยิงสแปม"),
                RubricScore("4. รายงานเอกสารและการนำเสนอ", 25, 21, "อธิบายการเทรนข้อมูลได้ชัดเจน")
            ),
            teacherComment = "ความคิดสร้างสรรค์ดีมาก มีประโยชน์ต่อสิ่งแวดล้อม",
            status = "ตรวจแล้ว (90/100 A)"
        )
    )

    val creatorTemplates = listOf(
        CreatorTemplate(
            id = "tmpl_1",
            title = "มินิเกมตอบคำถามชิงรางวัล (Quiz Gamification)",
            subject = SubjectCategory.SCIENCE,
            level = EducationLevel.PRIMARY,
            icon = "🎮",
            description = "เทมเพลตคำถาม 4 ตัวเลือก พร้อมระบบจับเวลา 15 วินาที สตรีคคะแนน และเสียงเอฟเฟกต์"
        ),
        CreatorTemplate(
            id = "tmpl_2",
            title = "บัตรคำศัพท์อัจฉริยะสองภาษา (Bilingual Flashcards)",
            subject = SubjectCategory.LANGUAGES,
            level = EducationLevel.KINDERGARTEN,
            icon = "🃏",
            description = "เทมเพลตการ์ดภาพพลิกได้ พร้อมระบบเสียงพากย์สังเคราะห์ TTS ภาษาไทยและอังกฤษ"
        ),
        CreatorTemplate(
            id = "tmpl_3",
            title = "ตัวจำลองกราฟ / ห้องทดลองเสมือน (Interactive Lab)",
            subject = SubjectCategory.MATHEMATICS,
            level = EducationLevel.SECONDARY,
            icon = "🧪",
            description = "เครื่องมือกราฟฟังก์ชันและตัวต้านทานไฟฟ้าที่ผู้เรียนสามารถเลื่อน Slider ดูผลการเปลี่ยนแปลงทันที"
        ),
        CreatorTemplate(
            id = "tmpl_4",
            title = "คู่มือช่างเทคนิค Step-by-Step (Technical Guide)",
            subject = SubjectCategory.TECH_VOCATIONAL,
            level = EducationLevel.VOCATIONAL,
            icon = "📐",
            description = "ผังขั้นตอนปฏิบัติการช่าง โค้ดบล็อก และเช็กลิสต์ความปลอดภัยก่อนลงมือทำ"
        )
    )
}
