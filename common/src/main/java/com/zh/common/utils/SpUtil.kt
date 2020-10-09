package com.zh.common.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Environment
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import java.io.*
import java.lang.ref.WeakReference

/**
 * @description :
 * @author :  zh
 * @date : 2019/5/6.
 */
object SpUtil {
    private var mSharedPreferences: SharedPreferences? = null
    private val SP_NAME = "config"
    private var context: WeakReference<Context>? = null

    /**
     * 初始化SharedPreferences，使用该类前必须先进行初始化
     *
     * @param context
     */
    fun init(context: Context) {
        this.context = WeakReference(context)
    }

    /**
     * 存储重要信息到sharedPreferences；
     *
     * @param key
     * @param value
     */
    fun setStringSF(key: String, value: String) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context?.get()?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        }
        mSharedPreferences!!.edit().putString(key, value).commit()
    }

    /**
     * 返回存在sharedPreferences的信息
     *
     * @param key
     * @return
     */
    fun getStringSF(key: String): String {
        if (mSharedPreferences == null) {
            mSharedPreferences = context?.get()?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        }
        return mSharedPreferences?.getString(key, "").toString()
    }

    /**
     * 存储重要信息到sharedPreferences；
     *
     * @param key
     * @param value
     */
    fun setBooleanSF(key: String, value: Boolean) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context?.get()?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        }
        mSharedPreferences!!.edit().putBoolean(key, value).commit()
    }

    /**
     * 返回存在sharedPreferences的信息
     *
     * @param key
     * @return
     */
    fun getBooleanSF(key: String): Boolean {
        if (mSharedPreferences == null) {
            mSharedPreferences = context?.get()?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        }
        return mSharedPreferences!!.getBoolean(key, false)
    }

    /**
     * 存储重要信息到sharedPreferences；
     *
     * @param key
     * @param value
     */
    fun setIntergerSF(key: String, value: Int) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context?.get()?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        }
        mSharedPreferences!!.edit().putInt(key, value).commit()
    }

    /**
     * 返回存在sharedPreferences的信息
     *
     * @param key
     * @return
     */
    fun getIntergerSF(key: String): Int {
        if (mSharedPreferences == null) {
            mSharedPreferences = context?.get()?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        }
        return mSharedPreferences!!.getInt(key, -1)
    }

    /**
     * 存储重要信息到sharedPreferences；
     *
     * @param key
     * @param value
     */
    fun setFloatSF(key: String, value: Float) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context?.get()?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        }
        mSharedPreferences!!.edit().putFloat(key, value).commit()
    }

    /**
     * 返回存在sharedPreferences的信息
     *
     * @param key
     * @return
     */
    fun getFloatSF(key: String): Float {
        if (mSharedPreferences == null) {
            mSharedPreferences = context?.get()?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        }
        return mSharedPreferences!!.getFloat(key, -1f)
    }

    /**
     * 清除某个内容
     */
    fun removeSF(key: String) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context?.get()?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        }
        mSharedPreferences!!.edit().remove(key).commit()
    }

    /**
     * 清除Shareprefrence
     */
    fun clearShareprefrence() {
        if (mSharedPreferences == null) {
            mSharedPreferences = context?.get()?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        }
        mSharedPreferences!!.edit().clear().commit()
    }

    /**
     * 将对象储存到sharepreference
     *
     * @param key
     * @param device
     * @param <T>
    </T> */
    fun <T> saveDeviceData(key: String?, device: T): Boolean {
        if (mSharedPreferences == null) {
            mSharedPreferences = context?.get()?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        }
        val baos = ByteArrayOutputStream()
        return try {   //Device为自定义类
            // 创建对象输出流，并封装字节流
            val oos = ObjectOutputStream(baos)
            // 将对象写入字节流
            oos.writeObject(device)
            // 将字节流编码成base64的字符串
            val oAuth_Base64 = String(
                Base64.encode(
                    baos
                        .toByteArray(), Base64.DEFAULT
                )
            )
            mSharedPreferences?.edit()?.putString(key, oAuth_Base64)!!.commit()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 将对象从shareprerence中取出来
     *
     * @param key
     * @param <T>
     * @return
    </T> */
    fun <T> getDeviceData(key: String?): T? {
        if (mSharedPreferences == null) {
            mSharedPreferences = context?.get()?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        }
        var device: T? = null
        val productBase64 = mSharedPreferences?.getString(key, null) ?: return null
        // 读取字节
        val base64 = Base64.decode(productBase64.toByteArray(), Base64.DEFAULT)

        // 封装到字节流
        val bais = ByteArrayInputStream(base64)
        try {
            // 再次封装
            val bis = ObjectInputStream(bais)
            // 读取对象
            device = bis.readObject() as T
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return device
    }

    /**
     * 返回缓存文件夹
     */
    val cacheFile: File
        get() = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            var file: File? = null
            file = context?.get()?.externalCacheDir //获取系统管理的sd卡缓存文件
            if (file == null) { //如果获取的为空,就是用自己定义的缓存文件夹做缓存路径
                file = File(cacheFilePath)
                if (!file.exists()) {
                    file.mkdirs()
                }
            }
            file
        } else {
            context?.get()?.cacheDir!!
        }

    /**
     * 获取自定义缓存文件地址
     *
     * @return
     */
    val cacheFilePath: String
        get() {
            val packageName = context?.get()?.packageName
            return "/mnt/sdcard/$packageName"
        }

    /**
     * 使用递归获取目录文件大小
     *
     * @param dir
     * @return
     */
    fun getDirSize(dir: File?): Long {
        if (dir == null) return 0
        if (!dir.isDirectory) return 0
        var dirSize: Long = 0
        val files = dir.listFiles()
        for (file in files) {
            if (file.isFile) {
                dirSize += file.length()
            } else if (file.isDirectory) {
                dirSize += file.length()
                dirSize += getDirSize(file) // 递归调用继续统计
            }
        }
        return dirSize
    }

    /**
     * 使用递归删除文件夹
     *
     * @param dir
     * @return
     */
    fun DeleteDir(dir: File?): Boolean {
        if (dir == null) return false
        if (!dir.isDirectory) return false
        val files = dir.listFiles()
        for (file in files) {
            if (file.isFile) {
                file.delete()
            } else if (file.isDirectory) {
                DeleteDir(file) // 递归调用继续删除
            }
        }
        return true
    }

    @Throws(IOException::class)
    fun BytyToString(`in`: InputStream): String {
        val out = ByteArrayOutputStream()
        val buf = ByteArray(1024)
        var num = 0
        while (`in`.read(buf).also { num = it } != -1) {
            out.write(buf, 0, buf.size)
        }
        val result = out.toString()
        out.close()
        return result
    }

    /**
     * desc:保存对象
     *
     * @param key
     * @param obj     要保存的对象，只能保存实现了serializable的对象
     * modified:
     */
    fun saveObject(key: String?, obj: Any?) {
        try {
            // 保存对象
            val sharedata = context?.get()?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)!!.edit()
            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            val bos = ByteArrayOutputStream()
            val os = ObjectOutputStream(bos)
            //将对象序列化写入byte缓存
            os.writeObject(obj)
            //将序列化的数据转为16进制保存
            val bytesToHexString = bytesToHexString(bos.toByteArray())
            //保存该16进制数组
            sharedata.putString(key, bytesToHexString)
            sharedata.commit()
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("", "保存obj失败")
        }
    }

    /**
     * desc:将数组转为16进制
     *
     * @param bArray
     * @return modified:
     */
    fun bytesToHexString(bArray: ByteArray?): String? {
        if (bArray == null) return null
        if (bArray.isEmpty()) return ""
        val sb = StringBuffer(bArray.size)
        var sTemp: String
        for (i in bArray.indices) {
            sTemp = Integer.toHexString(0xFF and bArray[i].toInt())
            if (sTemp.length < 2) sb.append(0)
            sb.append(sTemp.toUpperCase())
        }
        return sb.toString()
    }

    /**
     * desc:获取保存的Object对象
     *
     * @param key
     * @return modified:
     */
    fun readObject(key: String?): Any? {
        try {
            val sharedata = context?.get()?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            if (sharedata!!.contains(key)) {
                val string = sharedata.getString(key, "")
                return if (TextUtils.isEmpty(string)) {
                    null
                } else {
                    //将16进制的数据转为数组，准备反序列化
                    val stringToBytes = StringToBytes(string)
                    val bis =
                        ByteArrayInputStream(stringToBytes)
                    val `is` = ObjectInputStream(bis)
                    //返回反序列化得到的对象
                    `is`.readObject()
                }
            }
        } catch (e: StreamCorruptedException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        //所有异常返回null
        return null
    }

    /**
     * desc:将16进制的数据转为数组
     *
     * 创建人：聂旭阳 , 2014-5-25 上午11:08:33
     *
     * @param data
     * @return modified:
     */
    fun StringToBytes(data: String?): ByteArray? {
        val hexString = data!!.toUpperCase().trim { it <= ' ' }
        if (hexString.length % 2 != 0) return null
        val retData = ByteArray(hexString.length / 2)
        var i = 0
        while (i < hexString.length) {
            var int_ch: Int // 两位16进制数转化后的10进制数
            val hex_char1 = hexString[i] ////两位16进制数中的第一位(高位*16)
            var int_ch3: Int
            int_ch3 =
                when (hex_char1) {
                    in '0'..'9' -> (hex_char1.toInt() - 48) * 16 //// 0 的Ascll - 48
                    in 'A'..'F' -> (hex_char1.toInt() - 55) * 16 //// A 的Ascll - 65
                    else -> return null
                }
            i++
            val hex_char2 = hexString[i] ///两位16进制数中的第二位(低位)
            var int_ch4: Int
            int_ch4 =
                when (hex_char2) {
                    in '0'..'9' -> hex_char2.toInt() - 48 //// 0 的Ascll - 48
                    in 'A'..'F' -> hex_char2.toInt() - 55 //// A 的Ascll - 65
                    else -> return null
                }
            int_ch = int_ch3 + int_ch4
            retData[i / 2] = int_ch.toByte() //将转化后的数放入Byte里
            i++
        }
        return retData
    }
}