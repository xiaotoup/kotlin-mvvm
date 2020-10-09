package com.zh.common.http

import okhttp3.ResponseBody
import java.io.*

/**
 * @author: zxh
 * @Description: 下载文件的写入类
 */
class DownloadFileManager {
    /**
     * 下载到本地
     *
     * @param body     内容
     * @param filePath 文件目录
     * @param fileName 完整文件路径
     * @return 成功或者失败
     */
    private fun writeResponseBodyToDisk(
        body: ResponseBody,
        filePath: String,
        fileName: String
    ): Boolean {
        return try {
            //判断文件夹是否存在
            val files = File(filePath) //跟目录一个文件夹
            if (!files.exists()) {
                //不存在就创建出来
                files.mkdirs()
            }
            //创建一个文件
            val futureStudioIconFile = File(fileName)
            if (futureStudioIconFile.exists()) futureStudioIconFile.delete()
            //初始化输入流
            var inputStream: InputStream? = null
            //初始化输出流
            var outputStream: OutputStream? = null
            try {
                //设置每次读写的字节
                val fileReader = ByteArray(4096)
                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0
                //请求返回的字节流
                inputStream = body.byteStream()
                //创建输出流
                outputStream = FileOutputStream(futureStudioIconFile)
                //进行读取操作
                while (true) {
                    val read = inputStream.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    //进行写入操作
                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                }

                //刷新
                outputStream.flush()
                true
            } catch (e: IOException) {
                false
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            false
        }
    }
}