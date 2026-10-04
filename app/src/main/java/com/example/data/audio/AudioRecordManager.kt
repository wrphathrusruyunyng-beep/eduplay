package com.example.data.audio

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

class AudioRecordManager(private val context: Context) {

    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var currentRecordFile: File? = null

    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _hasRecording = MutableStateFlow(false)
    val hasRecording: StateFlow<Boolean> = _hasRecording.asStateFlow()

    fun startRecording(): Boolean {
        return try {
            stopPlaying()
            val file = File(context.cacheDir, "reading_practice_${System.currentTimeMillis()}.mp4")
            currentRecordFile = file

            val recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }

            recorder.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioEncodingBitRate(128000)
                setAudioSamplingRate(44100)
                setOutputFile(file.absolutePath)
                prepare()
                start()
            }
            mediaRecorder = recorder
            _isRecording.value = true
            true
        } catch (e: Exception) {
            Log.e("AudioRecordManager", "Failed to start recording: ${e.message}")
            _isRecording.value = false
            false
        }
    }

    fun stopRecording(): File? {
        return try {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null
            _isRecording.value = false
            _hasRecording.value = currentRecordFile != null && currentRecordFile?.exists() == true
            currentRecordFile
        } catch (e: Exception) {
            Log.e("AudioRecordManager", "Failed to stop recording: ${e.message}")
            mediaRecorder = null
            _isRecording.value = false
            null
        }
    }

    fun playRecording() {
        val file = currentRecordFile ?: return
        if (!file.exists()) return

        try {
            stopPlaying()
            val player = MediaPlayer().apply {
                setDataSource(file.absolutePath)
                prepare()
                setOnCompletionListener {
                    _isPlaying.value = false
                }
                start()
            }
            mediaPlayer = player
            _isPlaying.value = true
        } catch (e: Exception) {
            Log.e("AudioRecordManager", "Failed to play audio: ${e.message}")
            _isPlaying.value = false
        }
    }

    fun stopPlaying() {
        try {
            mediaPlayer?.apply {
                if (isPlaying) stop()
                release()
            }
            mediaPlayer = null
            _isPlaying.value = false
        } catch (e: Exception) {
            Log.e("AudioRecordManager", "Failed to release player: ${e.message}")
        }
    }

    fun release() {
        try {
            mediaRecorder?.release()
            mediaRecorder = null
            mediaPlayer?.release()
            mediaPlayer = null
        } catch (e: Exception) {
            Log.e("AudioRecordManager", "Release error: ${e.message}")
        }
    }
}
