package com.example.data.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TtsManager(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)
    private var isInitialized = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isInitialized = true
            // Check Thai support or fallback to English
            val thResult = tts?.setLanguage(Locale("th", "TH"))
            if (thResult == TextToSpeech.LANG_MISSING_DATA || thResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.w("TtsManager", "Thai language not supported on this TTS engine, setting US English")
                tts?.language = Locale.US
            }
            tts?.setSpeechRate(0.9f)
            tts?.setPitch(1.0f)
        } else {
            Log.e("TtsManager", "Failed to initialize TextToSpeech")
        }
    }

    fun speak(text: String, isEnglish: Boolean = false) {
        if (!isInitialized || tts == null) return

        try {
            val targetLocale = if (isEnglish) Locale.US else Locale("th", "TH")
            val supported = tts?.setLanguage(targetLocale)
            if (supported == TextToSpeech.LANG_MISSING_DATA || supported == TextToSpeech.LANG_NOT_SUPPORTED) {
                // fallback
                tts?.language = Locale.US
            }
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "edu_speech_${System.currentTimeMillis()}")
        } catch (e: Exception) {
            Log.e("TtsManager", "Speech error: ${e.message}")
        }
    }

    fun stop() {
        try {
            tts?.stop()
        } catch (e: Exception) {
            Log.e("TtsManager", "Error stopping TTS: ${e.message}")
        }
    }

    fun shutdown() {
        try {
            tts?.stop()
            tts?.shutdown()
            tts = null
            isInitialized = false
        } catch (e: Exception) {
            Log.e("TtsManager", "Error shutting down TTS: ${e.message}")
        }
    }
}
