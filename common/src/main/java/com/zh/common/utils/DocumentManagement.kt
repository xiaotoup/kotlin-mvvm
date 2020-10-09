package com.zh.common.utils

import android.util.Base64
import android.util.Log
import okhttp3.internal.and
import java.io.*
import java.security.MessageDigest
import kotlin.experimental.and

/**
 * 文件切割
 */
object DocumentManagement {
    // 判断文件是否存在
    fun isFileExist(file: File): Boolean {
        return file.exists()
    }

    @Throws(Exception::class)
    fun checkExist(filepath: String?): File {
        val file = File(filepath)
        if (file.exists()) { //判断文件目录的存在
            println("文件夹存在！")
            if (file.isDirectory) { //判断文件的存在性
                println("文件存在！")
            } else {
//                file.createNewFile();//创建文件
                println("文件不存在，创建文件成功！")
            }
        } else {
            println("文件夹不存在！")
            val file2 = File(file.parent)
            file2.mkdirs()
            println("创建文件夹成功！")
            if (file.isDirectory) {
                println("文件存在！")
            } else {
//                file.createNewFile();//创建文件
                println("文件不存在，创建文件成功！")
            }
        }
        return file
    }

    fun log(string: String) {
        Log.d("GsLog", "GsLog: $string")
    }
    //=======================================================================================
    /**
     * 文件分割方法
     *
     * @param targetFile 分割的文件
     * @param cutSize    分割文件的大小
     * @return int 文件切割的个数
     */
    fun getSplitFile(targetFile: File, cutSize: Long): Int {

        //计算切割文件大小
        val count =
            if (targetFile.length() % cutSize == 0L) (targetFile.length() / cutSize).toInt() else (targetFile.length() / cutSize + 1).toInt()
        var raf: RandomAccessFile? = null
        try {
            //获取目标文件 预分配文件所占的空间 在磁盘中创建一个指定大小的文件   r 是只读
            raf = RandomAccessFile(targetFile, "r")
            val length = raf.length() //文件的总长度
            val maxSize = length / count //文件切片后的长度
            var offSet = 0L //初始化偏移量
            for (i in 0 until count - 1) { //最后一片单独处理
                val begin = offSet
                val end = (i + 1) * maxSize
                offSet = getWrite(targetFile.absolutePath, i, begin, end)
            }
            if (length - offSet > 0) {
                getWrite(targetFile.absolutePath, count - 1, offSet, length)
            }
        } catch (e: FileNotFoundException) {
//            System.out.println("没有找到文件");
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                raf!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return count
    }

    /**
     * 指定文件每一份的边界，写入不同文件中
     *
     * @param file  源文件地址
     * @param index 源文件的顺序标识
     * @param begin 开始指针的位置
     * @param end   结束指针的位置
     * @return long
     */
    fun getWrite(
        file: String,
        index: Int,
        begin: Long,
        end: Long
    ): Long {
        var endPointer = 0L
        val a =
            file.split(suffixName(File(file)).toRegex()).toTypedArray()[0]
        try {
            //申明文件切割后的文件磁盘
            val `in` = RandomAccessFile(File(file), "r")
            //定义一个可读，可写的文件并且后缀名为.tmp的二进制文件
            //读取切片文件
            val mFile = File(a + "_" + index + ".tmp")
            //如果存在
            if (!isFileExist(mFile)) {
                val out = RandomAccessFile(mFile, "rw")
                //申明具体每一文件的字节数组
                val b = ByteArray(1024)
                var n = 0
                //从指定位置读取文件字节流
                `in`.seek(begin)
                //判断文件流读取的边界
                while (`in`.read(b).also { n = it } != -1 && `in`.filePointer <= end) {
                    //从指定每一份文件的范围，写入不同的文件
                    out.write(b, 0, n)
                }

                //定义当前读取文件的指针
                endPointer = `in`.filePointer
                //关闭输入流
                `in`.close()
                //关闭输出流
                out.close()
            } else {
                //不存在
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return endPointer - 1024
    }

    /**
     * 文件合并
     *
     * @param fileName   指定合并文件
     * @param targetFile 分割前的文件
     * @param cutSize    分割文件的大小
     */
    fun merge(
        fileName: String,
        targetFile: File,
        cutSize: Long
    ) {
        val tempCount =
            if (targetFile.length() % cutSize == 0L) (targetFile.length() / cutSize).toInt() else (targetFile.length() / cutSize + 1).toInt()
        //文件名
        val a =
            targetFile.absolutePath.split(suffixName(targetFile).toRegex())
                .toTypedArray()[0]
        var raf: RandomAccessFile? = null
        try {
            //申明随机读取文件RandomAccessFile
            raf = RandomAccessFile(
                File(
                    fileName + suffixName(targetFile)
                ), "rw"
            )
            //开始合并文件，对应切片的二进制文件
            for (i in 0 until tempCount) {
                //读取切片文件
                val mFile = File(a + "_" + i + ".tmp")
                //
                val reader = RandomAccessFile(mFile, "r")
                val b = ByteArray(1024)
                var n = 0
                //先读后写
                while (reader.read(b).also { n = it } != -1) { //读
                    raf.write(b, 0, n) //写
                }
                //合并后删除文件
                isDeleteFile(mFile)
                //日志
                log(mFile.toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                raf!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 文件MD5加密处理
     *
     * @param file 指定合并文件
     * @return String
     */
    fun getFileMD5(file: File): String? {
        if (!file.isFile) {
            return null
        }
        var digest: MessageDigest? = null
        var `in`: FileInputStream? = null
        val buffer = ByteArray(1024)
        var len: Int
        try {
            digest = MessageDigest.getInstance("MD5")
            `in` = FileInputStream(file)
            while (`in`.read(buffer, 0, 1024).also { len = it } != -1) {
                digest.update(buffer, 0, len)
            }
            `in`.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return bytesToHexString(digest.digest())
    }

    fun bytesToHexString(src: ByteArray?): String? {
        val stringBuilder = StringBuilder("")
        if (src == null || src.size <= 0) {
            return null
        }
        for (i in src.indices) {
            val v: Int = src[i] and 0xFF
            val hv = Integer.toHexString(v)
            if (hv.length < 2) {
                stringBuilder.append(0)
            }
            stringBuilder.append(hv)
        }
        return stringBuilder.toString()
    }

    /**
     * 对文件Base64加密处理
     *
     * @param file 指定加密处理文件
     * @return String
     */
    fun getBase64(file: File): String? {
        val filePath = file.absolutePath
        var `in`: InputStream? = null
        var buffer: ByteArray? = null
        try {
            `in` = FileInputStream(filePath)
            val bos = ByteArrayOutputStream()
            val b = ByteArray(1024)
            var n: Int
            while (`in`.read(b).also { n = it } != -1) {
                bos.write(b, 0, n)
            }
            `in`.close()
            bos.close()
            buffer = bos.toByteArray()
            `in`.close()
            return encodeByte(buffer)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * Base64加密处理
     *
     * @param buffer 指定加密处理字段
     * @return String 加密后
     */
    fun encodeByte(buffer: ByteArray?): String {
        return Base64.encodeToString(buffer, Base64.DEFAULT)
    }

    /**
     * Base64解码器处理
     *
     * @param base64Token 指定加密字段处理
     * @return String 加密后
     */
    fun docodeByte(base64Token: String?): ByteArray {
        return Base64.decode(base64Token, Base64.DEFAULT) // 解码后
    }

    /**
     * 获取文件后缀名 例如：.mp4 /.jpg /.apk
     *
     * @param file 指定文件
     * @return String 文件后缀名
     */
    fun suffixName(file: File): String {
        val fileName = file.name
        return fileName.substring(fileName.lastIndexOf("."), fileName.length)
    }

    /**
     * 删除文件
     *
     * @param file 指定文件
     * @return boolean
     */
    fun isDeleteFile(file: File): Boolean {
        return file.delete()
    }
}