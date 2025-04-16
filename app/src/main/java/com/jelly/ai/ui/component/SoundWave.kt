package com.jelly.ai.ui.component

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Environment
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.sin

@Composable
fun EnhancedSoundWaveVisualizer() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // 音频处理参数
    val sampleRate = 44100
    val channelConfig = AudioFormat.CHANNEL_IN_MONO
    val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

    // 音量值状态
    var isRecording by remember { mutableStateOf(false) }
    var amplitude by remember { mutableStateOf(0f) }
    var isRecordingSaved by remember { mutableStateOf(false) }
    var savedFilePath by remember { mutableStateOf("") }

    // 动画参数 - 增加弹性使波动更明显
    val animatedAmplitude by animateFloatAsState(
        targetValue = amplitude,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,  // 增加弹性
            stiffness = Spring.StiffnessLow
        )
    )

    // 用于保存PCM数据的列表
    val pcmData = remember { mutableStateListOf<Short>() }

    // 启动录音的函数
    fun startRecording() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 这里应添加权限请求逻辑
            return
        }

        // 清空之前的录音数据
        pcmData.clear()
        isRecordingSaved = false
        savedFilePath = ""

        coroutineScope.launch(Dispatchers.IO) {
            try {
                val audioRecord = AudioRecord(
                    MediaRecorder.AudioSource.MIC,
                    sampleRate,
                    channelConfig,
                    audioFormat,
                    bufferSize
                )

                val buffer = ShortArray(bufferSize)
                audioRecord.startRecording()
                isRecording = true

                while (isRecording) {
                    val readSize = audioRecord.read(buffer, 0, bufferSize)
                    if (readSize > 0) {
                        // 存储PCM数据
                        for (i in 0 until readSize) {
                            pcmData.add(buffer[i])
                        }

                        // 计算音量大小
                        var sum = 0.0
                        for (i in 0 until readSize) {
                            sum += abs(buffer[i].toDouble())
                        }

                        // 将音量归一化为0-1之间的值，增大系数使波动更明显
                        val avgAmplitude = (sum / readSize) / Short.MAX_VALUE
                        withContext(Dispatchers.Main) {
                            amplitude = (avgAmplitude * 5).toFloat().coerceIn(0f, 1f)  // 增大系数从3到5
                        }
                    }
                }

                audioRecord.stop()
                audioRecord.release()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun stopRecording() {
        isRecording = false
    }

    fun saveRecording() {
        if (pcmData.isEmpty()) return

        coroutineScope.launch(Dispatchers.IO) {
            try {
                // 创建保存录音的文件
                val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                val fileName = "AUDIO_$timeStamp.pcm"
                val file = File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), fileName)

                FileOutputStream(file).use { fos ->
                    val shortBuffer = pcmData.toShortArray()
                    val byteBuffer = ByteArray(shortBuffer.size * 2)

                    // 将short数组转换为byte数组
                    for (i in shortBuffer.indices) {
                        byteBuffer[i * 2] = (shortBuffer[i].toInt() and 0xff).toByte()
                        byteBuffer[i * 2 + 1] = (shortBuffer[i].toInt() shr 8 and 0xff).toByte()
                    }

                    fos.write(byteBuffer)
                }

                withContext(Dispatchers.Main) {
                    isRecordingSaved = true
                    savedFilePath = file.absolutePath
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 声纹可视化组件
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            EnhancedWaveformVisualizer(
                modifier = Modifier.fillMaxSize(),
                amplitude = animatedAmplitude
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 控制按钮
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                if (!isRecording) startRecording()
            }) {
                Text("开始录音")
            }

            Button(onClick = {
                if (isRecording) stopRecording()
            }) {
                Text("停止录音")
            }

            Button(
                onClick = { saveRecording() },
                enabled = !isRecording && pcmData.isNotEmpty() && !isRecordingSaved
            ) {
                Text("保存录音")
            }
        }

        // 显示保存状态
        if (isRecordingSaved) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("录音已保存到: $savedFilePath")
        }
    }
}

@Composable
fun EnhancedWaveformVisualizer(
    modifier: Modifier = Modifier,
    amplitude: Float = 0f,
    waveColor: Color = Color(0xFF4CAF50)
) {
    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val centerY = canvasHeight / 2

        // 波形参数 - 增大最大振幅
        val maxAmplitude = canvasHeight / 2 * amplitude * 1.5f  // 增大振幅系数

        // 绘制中间线
        drawLine(
            color = waveColor.copy(alpha = 0.5f),
            start = Offset(0f, centerY),
            end = Offset(canvasWidth, centerY),
            strokeWidth = 1f
        )

        // 绘制波形 - 增加密度
        val segmentWidth = 4f  // 降低宽度，增加密度（从8降为4）
        val segments = (canvasWidth / segmentWidth).toInt()

        // 创建路径来绘制上半部分波形
        val upperPath = Path()
        val lowerPath = Path()

        upperPath.moveTo(0f, centerY)
        lowerPath.moveTo(0f, centerY)

        for (i in 0 until segments) {
            val x = i * segmentWidth

            // 创建多个正弦波叠加，调整频率使波形更复杂
            val wave1 = sin(x * 0.04f) * maxAmplitude  // 增加频率
            val wave2 = sin(x * 0.06f) * maxAmplitude * 0.6f  // 增加频率和比例
            val wave3 = sin(x * 0.02f) * maxAmplitude * 0.4f
            val wave4 = sin(x * 0.08f) * maxAmplitude * 0.3f  // 增加一个高频分量

            val waveHeight = (wave1 + wave2 + wave3 + wave4) * amplitude

            // 添加到路径
            upperPath.lineTo(x, centerY - waveHeight)
            lowerPath.lineTo(x, centerY + waveHeight)

            // 绘制垂直线条，增强声纹效果
            drawLine(
                color = waveColor,
                start = Offset(x, centerY - waveHeight),
                end = Offset(x, centerY + waveHeight),
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )
        }

        // 完成路径
        upperPath.lineTo(canvasWidth, centerY)
        lowerPath.lineTo(canvasWidth, centerY)

        // 绘制上下波形曲线
        drawPath(
            path = upperPath,
            color = waveColor.copy(alpha = 0.5f),
            style = Stroke(width = 2f)
        )

        drawPath(
            path = lowerPath,
            color = waveColor.copy(alpha = 0.5f),
            style = Stroke(width = 2f)
        )
    }
}

// 在你的主Activity或Composable中调用
@Composable
fun AudioVisualizerAndRecorder() {
    EnhancedSoundWaveVisualizer()
}