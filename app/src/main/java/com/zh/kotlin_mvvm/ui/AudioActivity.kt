package com.zh.kotlin_mvvm.ui

import android.media.AudioFormat
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.zh.common.base.BaseActivity
import com.zh.common.base.viewmodel.BaseViewModel
import com.zh.common.base.viewmodel.NormalViewModel
import com.zh.kotlin_mvvm.R
import kotlinx.android.synthetic.main.activity_audio.*

class AudioActivity(
    override val layoutRes: Int = R.layout.activity_audio,
    override val viewModel: BaseViewModel = NormalViewModel()
) : BaseActivity<ViewDataBinding>() {

    // 采样率，现在能够保证在所有设备上使用的采样率是44100Hz, 但是其他的采样率（22050, 16000, 11025）在一些设备上也可以使用。
    public val SAMPLE_RATE_INHZ = 44100

    // 声道数。CHANNEL_IN_MONO and CHANNEL_IN_STEREO. 其中CHANNEL_IN_MONO是可以保证在所有设备能够使用的。
    public val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO

    // 返回的音频数据的格式。 ENCODING_PCM_8BIT, ENCODING_PCM_16BIT, and ENCODING_PCM_FLOAT.
    public val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT

    override fun initView(savedInstanceState: Bundle?) {
        button10.setOnClickListener {

        }
    }

//    private lateinit var audioRecord: AudioRecord private
//    val TAG = "FragmentActivity"
//
//    // 开始录音
//    private fun startRecord() {
//        // 获取最小录音缓存大小，
//        val minBufferSize =
//            AudioRecord.getMinBufferSize(SAMPLE_RATE_INHZ, CHANNEL_CONFIG, AUDIO_FORMAT)
//        audioRecord = AudioRecord(
//            MediaRecorder.AudioSource.MIC, SAMPLE_RATE_INHZ,
//            CHANNEL_CONFIG, AUDIO_FORMAT, minBufferSize
//        )
//        // 初始化缓存
//        val data: Byte = byte[minBufferSize]
//        val file = File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "hello/test.pcm")
//        Log.i(TAG, "文件位置 path:" + file.getAbsolutePath())
//        if (!createFile(file)) return
//        // 开始录音
//        audioRecord.startRecording()
//        isRecording = true;
//        // 创建数据流，将缓存导入数据流
//        workHandler.post(new Runnable () {
//            @Override
//            public void run() {
//                var fos: FileOutputStream? = null
//                try {
//                    fos = FileOutputStream(file)
//                } catch (e: FileNotFoundException) {
//                    e.printStackTrace()
//                    Log.e(TAG, "文件未找到")
//                }
//                if (fos == null) return@run
//                while (isRecording) {
//                    val length = audioRecord . read (data, 0, minBufferSize);
//                    if (AudioRecord.ERROR_INVALID_OPERATION != length) {
//                        try {
//                            fos?.write(data, 0, length)
//                        } catch (e: IOException) {
//                            e.printStackTrace()
//                        }
//                    }
//                }
//                try {
//                    // 关闭数据流
//                    fos?.close()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }
//        })
//    }
}