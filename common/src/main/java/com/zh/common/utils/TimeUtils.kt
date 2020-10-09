package com.zh.common.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * TimeUtils
 */
class TimeUtils private constructor() {
    companion object {
        /**
         *
         * 在工具类中经常使用到工具类的格式化描述，这个主要是一个日期的操作类，所以日志格式主要使用 SimpleDateFormat的定义格式.
         * 格式的意义如下： 日期和时间模式 <br></br>
         *
         * 日期和时间格式由日期和时间模式字符串指定。在日期和时间模式字符串中，未加引号的字母 'A' 到 'Z' 和 'a' 到 'z'
         * 被解释为模式字母，用来表示日期或时间字符串元素。文本可以使用单引号 (') 引起来，以免进行解释。"''"
         * 表示单引号。所有其他字符均不解释；只是在格式化时将它们简单复制到输出字符串，或者在分析时与输入字符串进行匹配。
         *
         * 定义了以下模式字母（所有其他字符 'A' 到 'Z' 和 'a' 到 'z' 都被保留）： <br></br>
         * <table border="1" cellspacing="1" cellpadding="1" summary="Chart shows pattern letters, date/time component,
        presentation, and examples.">
         * <tr>
         * <th align="left">字母</th>
         * <th align="left">日期或时间元素</th>
         * <th align="left">表示</th>
         * <th align="left">示例</th>
        </tr> *
         * <tr>
         * <td>`G`</td>
         * <td>Era 标志符</td>
         * <td>Text</td>
         * <td>`AD`</td>
        </tr> *
         * <tr>
         * <td>`y` </td>
         * <td>年 </td>
         * <td>Year </td>
         * <td>`1996`; `96` </td>
        </tr> *
         * <tr>
         * <td>`M` </td>
         * <td>年中的月份 </td>
         * <td>Month </td>
         * <td>`July`; `Jul`; `07` </td>
        </tr> *
         * <tr>
         * <td>`w` </td>
         * <td>年中的周数 </td>
         * <td>Number </td>
         * <td>`27` </td>
        </tr> *
         * <tr>
         * <td>`W` </td>
         * <td>月份中的周数 </td>
         * <td>Number </td>
         * <td>`2` </td>
        </tr> *
         * <tr>
         * <td>`D` </td>
         * <td>年中的天数 </td>
         * <td>Number </td>
         * <td>`189` </td>
        </tr> *
         * <tr>
         * <td>`d` </td>
         * <td>月份中的天数 </td>
         * <td>Number </td>
         * <td>`10` </td>
        </tr> *
         * <tr>
         * <td>`F` </td>
         * <td>月份中的星期 </td>
         * <td>Number </td>
         * <td>`2` </td>
        </tr> *
         * <tr>
         * <td>`E` </td>
         * <td>星期中的天数 </td>
         * <td>Text </td>
         * <td>`Tuesday`; `Tue` </td>
        </tr> *
         * <tr>
         * <td>`a` </td>
         * <td>Am/pm 标记 </td>
         * <td>Text </td>
         * <td>`PM` </td>
        </tr> *
         * <tr>
         * <td>`H` </td>
         * <td>一天中的小时数（0-23） </td>
         * <td>Number </td>
         * <td>`0` </td>
        </tr> *
         * <tr>
         * <td>`k` </td>
         * <td>一天中的小时数（1-24） </td>
         * <td>Number </td>
         * <td>`24` </td>
        </tr> *
         * <tr>
         * <td>`K` </td>
         * <td>am/pm 中的小时数（0-11） </td>
         * <td>Number </td>
         * <td>`0` </td>
        </tr> *
         * <tr>
         * <td>`h` </td>
         * <td>am/pm 中的小时数（1-12） </td>
         * <td>Number </td>
         * <td>`12` </td>
        </tr> *
         * <tr>
         * <td>`m` </td>
         * <td>小时中的分钟数 </td>
         * <td>Number </td>
         * <td>`30` </td>
        </tr> *
         * <tr>
         * <td>`s` </td>
         * <td>分钟中的秒数 </td>
         * <td>Number </td>
         * <td>`55` </td>
        </tr> *
         * <tr>
         * <td>`S` </td>
         * <td>毫秒数 </td>
         * <td>Number </td>
         * <td>`978` </td>
        </tr> *
         * <tr>
         * <td>`z` </td>
         * <td>时区 </td>
         * <td>General time zone </td>
         * <td>`Pacific Standard Time`; `PST`; `GMT-08:00` </td>
        </tr> *
         * <tr>
         * <td>`Z` </td>
         * <td>时区 </td>
         * <td>RFC 822 time zone </td>
         * <td>`-0800` </td>
        </tr> *
        </table> *
         * <pre>
         * HH:mm    15:44
         * h:mm a    3:44 下午
         * HH:mm z    15:44 CST
         * HH:mm Z    15:44 +0800
         * HH:mm zzzz    15:44 中国标准时间
         * HH:mm:ss    15:44:40
         * yyyy-MM-dd    2016-08-12
         * yyyy-MM-dd HH:mm    2016-08-12 15:44
         * yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
         * yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中国标准时间
         * EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中国标准时间
         * yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
         * yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
         * yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
         * K:mm a    3:44 下午
         * EEE, MMM d, ''yy    星期五, 八月 12, '16
         * hh 'o''clock' a, zzzz    03 o'clock 下午, 中国标准时间
         * yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
         * EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
         * yyMMddHHmmssZ    160812154440+0800
         * yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
         * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    星期五 DATE(2016-08-12) TIME(15:44:40) 中国标准时间
        </pre> *
         * 注意：SimpleDateFormat不是线程安全的，线程安全需用`ThreadLocal<SimpleDateFormat>`
         */
        const val DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss"
        const val DEFAULT_PATTERN1 = "yyyy-MM-dd HH:mm"
        const val DEFAULT_PATTERN2 = "yyyy-MM-dd"
        const val DEFAULT_PATTERN3 = "yyyy.MM.dd"

        /**
         * 将时间戳转为时间字符串
         *
         * 格式为yyyy-MM-dd HH:mm:ss
         *
         * @param millis 毫秒时间戳
         * @return 时间字符串
         */
        fun millis2String(millis: Long): String {
            return SimpleDateFormat(
                DEFAULT_PATTERN,
                Locale.getDefault()
            ).format(Date(millis))
        }

        /**
         * 将时间戳转为时间字符串
         *
         * 格式为pattern
         *
         * @param millis 毫秒时间戳
         * @param pattern 时间格式
         * @return 时间字符串
         */
        fun millis2String(millis: Long, pattern: String?): String {
            return SimpleDateFormat(pattern, Locale.getDefault())
                .format(Date(millis))
        }
        /**
         * 将时间字符串转为时间戳
         *
         * time格式为pattern
         *
         * @param time 时间字符串
         * @param pattern 时间格式
         * @return 毫秒时间戳
         */
        /**
         * 将时间字符串转为时间戳
         *
         * time格式为yyyy-MM-dd HH:mm:ss
         *
         * @param time 时间字符串
         * @return 毫秒时间戳
         */
        @JvmOverloads
        fun string2Millis(
            time: String?,
            pattern: String? = DEFAULT_PATTERN
        ): Long {
            try {
                return SimpleDateFormat(pattern, Locale.getDefault())
                    .parse(time).time
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return -1
        }
        /**
         * 将时间字符串转为Date类型
         *
         * time格式为pattern
         *
         * @param time 时间字符串
         * @param pattern 时间格式
         * @return Date类型
         */
        /**
         * 将时间字符串转为Date类型
         *
         * time格式为yyyy-MM-dd HH:mm:ss
         *
         * @param time 时间字符串
         * @return Date类型
         */
        @JvmOverloads
        fun string2Date(
            time: String?,
            pattern: String? = DEFAULT_PATTERN
        ): Date {
            return Date(
                string2Millis(
                    time,
                    pattern
                )
            )
        }

        fun string2DateDiv1000(time: String?): Date? {
            var date: Date? = null
            val sdf =
                SimpleDateFormat("yyyy-MM-dd") //小写的mm表示的是分钟
            try {
                date = sdf.parse(time)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return date
        }
        /**
         * 将Date类型转为时间字符串
         *
         * 格式为pattern
         *
         * @param date Date类型时间
         * @param pattern 时间格式
         * @return 时间字符串
         */
        /**
         * 将Date类型转为时间字符串
         *
         * 格式为yyyy-MM-dd HH:mm:ss
         *
         * @param date Date类型时间
         * @return 时间字符串
         */
        @JvmOverloads
        fun date2String(
            date: Date?,
            pattern: String? = DEFAULT_PATTERN
        ): String {
            return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
        }

        /**
         * 将Date类型转为时间戳
         *
         * @param date Date类型时间
         * @return 毫秒时间戳
         */
        fun date2Millis(date: Date): Long {
            return date.time
        }

        /**
         * 将时间戳转为Date类型
         *
         * @param millis 毫秒时间戳
         * @return Date类型时间
         */
        fun millis2Date(millis: Long): Date {
            return Date(millis)
        }

        /**
         * 获取两个时间差（单位：unit）绝对值取值
         *
         * time0和time1格式都为yyyy-MM-dd HH:mm:ss
         *
         * @param time0 时间字符串0
         * @param time1 时间字符串1
         * @param unit 单位类型
         *
         *  * [TimeConstants.MSEC]: 毫秒
         *  * [TimeConstants.SEC]: 秒
         *  * [TimeConstants.MIN]: 分
         *  * [TimeConstants.HOUR]: 小时
         *  * [TimeConstants.DAY]: 天
         *
         * @return unit时间戳
         */
        fun getTimeSpan(
            time0: String?,
            time1: String?,
            @TimeConstants.Unit unit: Int
        ): Long {
            return getTimeSpan(
                time0,
                time1,
                unit,
                DEFAULT_PATTERN
            )
        }

        /**
         * 获取两个时间差（单位：unit）可以为负值
         *
         * time0和time1格式都为yyyy-MM-dd HH:mm:ss
         *
         * @param time0 时间字符串0
         * @param time1 时间字符串1
         * @param unit 单位类型
         *
         *  * [TimeConstants.MSEC]: 毫秒
         *  * [TimeConstants.SEC]: 秒
         *  * [TimeConstants.MIN]: 分
         *  * [TimeConstants.HOUR]: 小时
         *  * [TimeConstants.DAY]: 天
         *
         * @return unit时间戳
         */
        fun getTimeSpan1(
            time0: String?,
            time1: String?,
            @TimeConstants.Unit unit: Int
        ): Long {
            return getTimeSpan1(
                time0,
                time1,
                unit,
                DEFAULT_PATTERN
            )
        }

        /**
         * 获取两个时间差（单位：unit）
         *
         * time0和time1格式都为format
         *
         * @param time0 时间字符串0
         * @param time1 时间字符串1
         * @param unit 单位类型
         *
         *  * [TimeConstants.MSEC]: 毫秒
         *  * [TimeConstants.SEC]: 秒
         *  * [TimeConstants.MIN]: 分
         *  * [TimeConstants.HOUR]: 小时
         *  * [TimeConstants.DAY]: 天
         *
         * @param pattern 时间格式
         * @return unit时间戳
         */
        fun getTimeSpan(
            time0: String?,
            time1: String?,
            @TimeConstants.Unit unit: Int,
            pattern: String?
        ): Long {
            return millis2TimeSpan(
                Math.abs(
                    string2Millis(
                        time0,
                        pattern
                    ) - string2Millis(time1, pattern)
                ), unit
            )
        }

        /**
         * 获取两个时间差（单位：unit）
         *
         * time0和time1格式都为format
         *
         * @param time0 时间字符串0
         * @param time1 时间字符串1
         * @param unit 单位类型
         *
         *  * [TimeConstants.MSEC]: 毫秒
         *  * [TimeConstants.SEC]: 秒
         *  * [TimeConstants.MIN]: 分
         *  * [TimeConstants.HOUR]: 小时
         *  * [TimeConstants.DAY]: 天
         *
         * @param pattern 时间格式
         * @return unit时间戳
         */
        fun getTimeSpan1(
            time0: String?,
            time1: String?,
            @TimeConstants.Unit unit: Int,
            pattern: String?
        ): Long {
            return millis2TimeSpan(
                string2Millis(
                    time0,
                    pattern
                ) - string2Millis(time1, pattern), unit
            )
        }

        /**
         * 获取两个时间差（单位：unit）
         *
         * @param date0 Date类型时间0
         * @param date1 Date类型时间1
         * @param unit 单位类型
         *
         *  * [TimeConstants.MSEC]: 毫秒
         *  * [TimeConstants.SEC]: 秒
         *  * [TimeConstants.MIN]: 分
         *  * [TimeConstants.HOUR]: 小时
         *  * [TimeConstants.DAY]: 天
         *
         * @return unit时间戳
         */
        fun getTimeSpan(
            date0: Date,
            date1: Date,
            @TimeConstants.Unit unit: Int
        ): Long {
            return millis2TimeSpan(
                Math.abs(
                    date2Millis(
                        date0
                    ) - date2Millis(date1)
                ), unit
            )
        }

        /**
         * 获取两个时间差（单位：unit）
         *
         * @param millis0 毫秒时间戳0
         * @param millis1 毫秒时间戳1
         * @param unit 单位类型
         *
         *  * [TimeConstants.MSEC]: 毫秒
         *  * [TimeConstants.SEC]: 秒
         *  * [TimeConstants.MIN]: 分
         *  * [TimeConstants.HOUR]: 小时
         *  * [TimeConstants.DAY]: 天
         *
         * @return unit时间戳
         */
        fun getTimeSpan(
            millis0: Long,
            millis1: Long,
            @TimeConstants.Unit unit: Int
        ): Long {
            return millis2TimeSpan(
                Math.abs(
                    millis0 - millis1
                ), unit
            )
        }

        /**
         * 获取合适型两个时间差
         *
         * time0和time1格式都为yyyy-MM-dd HH:mm:ss
         *
         * @param time0 时间字符串0
         * @param time1 时间字符串1
         * @param precision 精度
         *
         * precision = 0，返回null
         *
         * precision = 1，返回天
         *
         * precision = 2，返回天和小时
         *
         * precision = 3，返回天、小时和分钟
         *
         * precision = 4，返回天、小时、分钟和秒
         *
         * precision &gt;= 5，返回天、小时、分钟、秒和毫秒
         * @return 合适型两个时间差
         */
        fun getFitTimeSpan(
            time0: String?,
            time1: String?,
            precision: Int
        ): String? {
            return millis2FitTimeSpan(
                Math.abs(
                    string2Millis(
                        time0,
                        DEFAULT_PATTERN
                    ) - string2Millis(
                        time1,
                        DEFAULT_PATTERN
                    )
                ), precision
            )
        }

        /**
         * 获取合适型两个时间差
         *
         * time0和time1格式都为pattern
         *
         * @param time0 时间字符串0
         * @param time1 时间字符串1
         * @param precision 精度
         *
         * precision = 0，返回null
         *
         * precision = 1，返回天
         *
         * precision = 2，返回天和小时
         *
         * precision = 3，返回天、小时和分钟
         *
         * precision = 4，返回天、小时、分钟和秒
         *
         * precision &gt;= 5，返回天、小时、分钟、秒和毫秒
         * @param pattern 时间格式
         * @return 合适型两个时间差
         */
        fun getFitTimeSpan(
            time0: String?,
            time1: String?,
            precision: Int,
            pattern: String?
        ): String? {
            return millis2FitTimeSpan(
                Math.abs(
                    string2Millis(
                        time0,
                        pattern
                    ) - string2Millis(time1, pattern)
                ), precision
            )
        }

        /**
         * 获取合适型两个时间差
         *
         * @param date0 Date类型时间0
         * @param date1 Date类型时间1
         * @param precision 精度
         *
         * precision = 0，返回null
         *
         * precision = 1，返回天
         *
         * precision = 2，返回天和小时
         *
         * precision = 3，返回天、小时和分钟
         *
         * precision = 4，返回天、小时、分钟和秒
         *
         * precision &gt;= 5，返回天、小时、分钟、秒和毫秒
         * @return 合适型两个时间差
         */
        fun getFitTimeSpan(
            date0: Date,
            date1: Date,
            precision: Int
        ): String? {
            return millis2FitTimeSpan(
                Math.abs(
                    date2Millis(
                        date0
                    ) - date2Millis(date1)
                ), precision
            )
        }

        /**
         * 获取合适型两个时间差
         *
         * @param millis0 毫秒时间戳1
         * @param millis1 毫秒时间戳2
         * @param precision 精度
         *
         * precision = 0，返回null
         *
         * precision = 1，返回天
         *
         * precision = 2，返回天和小时
         *
         * precision = 3，返回天、小时和分钟
         *
         * precision = 4，返回天、小时、分钟和秒
         *
         * precision &gt;= 5，返回天、小时、分钟、秒和毫秒
         * @return 合适型两个时间差
         */
        fun getFitTimeSpan(
            millis0: Long,
            millis1: Long,
            precision: Int
        ): String? {
            return millis2FitTimeSpan(
                Math.abs(
                    millis0 - millis1
                ), precision
            )
        }

        /**
         * 获取当前毫秒时间戳
         *
         * @return 毫秒时间戳
         */
        val nowTimeMills: Long
            get() = System.currentTimeMillis()

        /**
         * 获取当前时间字符串
         *
         * 格式为yyyy-MM-dd HH:mm:ss
         *
         * @return 时间字符串
         */
        val nowTimeString: String
            get() = millis2String(
                System.currentTimeMillis(),
                DEFAULT_PATTERN
            )

        /**
         * 获取当前时间字符串
         *
         * 格式为pattern
         *
         * @param pattern 时间格式
         * @return 时间字符串
         */
        fun getNowTimeString(pattern: String?): String {
            return millis2String(
                System.currentTimeMillis(),
                pattern
            )
        }

        /**
         * 获取当前Date
         *
         * @return Date类型时间
         */
        val nowTimeDate: Date
            get() = Date()

        val beforeTimeDate: Date
            get() {
                var date = Date()
                val calendar = Calendar.getInstance()
                calendar.time = date
                calendar.add(Calendar.DAY_OF_MONTH, -1)
                date = calendar.time
                return date
            }

        /**
         * 获取与当前时间的差（单位：unit）
         *
         * time格式为yyyy-MM-dd HH:mm:ss
         *
         * @param time 时间字符串
         * @param unit 单位类型
         *
         *  * [TimeConstants.MSEC]: 毫秒
         *  * [TimeConstants.SEC]: 秒
         *  * [TimeConstants.MIN]: 分
         *  * [TimeConstants.HOUR]: 小时
         *  * [TimeConstants.DAY]: 天
         *
         * @return unit时间戳
         */
        fun getTimeSpanByNow(time: String?, @TimeConstants.Unit unit: Int): Long {
            return getTimeSpan(
                nowTimeString,
                time,
                unit,
                DEFAULT_PATTERN
            )
        }

        /**
         * 获取与当前时间的差（单位：unit）
         *
         * time格式为pattern
         *
         * @param time 时间字符串
         * @param unit 单位类型
         *
         *  * [TimeConstants.MSEC]: 毫秒
         *  * [TimeConstants.SEC]: 秒
         *  * [TimeConstants.MIN]: 分
         *  * [TimeConstants.HOUR]: 小时
         *  * [TimeConstants.DAY]: 天
         *
         * @param pattern 时间格式
         * @return unit时间戳
         */
        fun getTimeSpanByNow(
            time: String?,
            @TimeConstants.Unit unit: Int,
            pattern: String?
        ): Long {
            return getTimeSpan(
                nowTimeString,
                time,
                unit,
                pattern
            )
        }

        /**
         * 获取与当前时间的差（单位：unit）
         *
         * @param date Date类型时间
         * @param unit 单位类型
         *
         *  * [TimeConstants.MSEC]: 毫秒
         *  * [TimeConstants.SEC]: 秒
         *  * [TimeConstants.MIN]: 分
         *  * [TimeConstants.HOUR]: 小时
         *  * [TimeConstants.DAY]: 天
         *
         * @return unit时间戳
         */
        fun getTimeSpanByNow(date: Date, @TimeConstants.Unit unit: Int): Long {
            return getTimeSpan(Date(), date, unit)
        }

        /**
         * 获取与当前时间的差（单位：unit）
         *
         * @param millis 毫秒时间戳
         * @param unit 单位类型
         *
         *  * [TimeConstants.MSEC]: 毫秒
         *  * [TimeConstants.SEC]: 秒
         *  * [TimeConstants.MIN]: 分
         *  * [TimeConstants.HOUR]: 小时
         *  * [TimeConstants.DAY]: 天
         *
         * @return unit时间戳
         */
        fun getTimeSpanByNow(millis: Long, @TimeConstants.Unit unit: Int): Long {
            return getTimeSpan(
                System.currentTimeMillis(),
                millis,
                unit
            )
        }

        /**
         * 获取合适型与当前时间的差
         *
         * time格式为yyyy-MM-dd HH:mm:ss
         *
         * @param time 时间字符串
         * @param precision 精度
         *
         *  * precision = 0，返回null
         *  * precision = 1，返回天
         *  * precision = 2，返回天和小时
         *  * precision = 3，返回天、小时和分钟
         *  * precision = 4，返回天、小时、分钟和秒
         *  * precision &gt;= 5，返回天、小时、分钟、秒和毫秒
         *
         * @return 合适型与当前时间的差
         */
        fun getFitTimeSpanByNow(time: String?, precision: Int): String? {
            return getFitTimeSpan(
                nowTimeString,
                time,
                precision,
                DEFAULT_PATTERN
            )
        }

        /**
         * 获取合适型与当前时间的差
         *
         * time格式为pattern
         *
         * @param time 时间字符串
         * @param precision 精度
         * @param pattern 时间格式
         *
         *  * precision = 0，返回null
         *  * precision = 1，返回天
         *  * precision = 2，返回天和小时
         *  * precision = 3，返回天、小时和分钟
         *  * precision = 4，返回天、小时、分钟和秒
         *  * precision &gt;= 5，返回天、小时、分钟、秒和毫秒
         *
         * @return 合适型与当前时间的差
         */
        fun getFitTimeSpanByNow(
            time: String?,
            precision: Int,
            pattern: String?
        ): String? {
            return getFitTimeSpan(
                nowTimeString,
                time,
                precision,
                pattern
            )
        }

        /**
         * 获取合适型与当前时间的差
         *
         * @param date Date类型时间
         * @param precision 精度
         *
         *  * precision = 0，返回null
         *  * precision = 1，返回天
         *  * precision = 2，返回天和小时
         *  * precision = 3，返回天、小时和分钟
         *  * precision = 4，返回天、小时、分钟和秒
         *  * precision &gt;= 5，返回天、小时、分钟、秒和毫秒
         *
         * @return 合适型与当前时间的差
         */
        fun getFitTimeSpanByNow(date: Date, precision: Int): String? {
            return getFitTimeSpan(
                nowTimeDate,
                date,
                precision
            )
        }

        /**
         * 获取合适型与当前时间的差
         *
         * @param millis 毫秒时间戳
         * @param precision 精度
         *
         *  * precision = 0，返回null
         *  * precision = 1，返回天
         *  * precision = 2，返回天和小时
         *  * precision = 3，返回天、小时和分钟
         *  * precision = 4，返回天、小时、分钟和秒
         *  * precision &gt;= 5，返回天、小时、分钟、秒和毫秒
         *
         * @return 合适型与当前时间的差
         */
        fun getFitTimeSpanByNow(millis: Long, precision: Int): String? {
            return getFitTimeSpan(
                System.currentTimeMillis(),
                millis,
                precision
            )
        }

        /**
         * 获取友好型与当前时间的差
         *
         * time格式为yyyy-MM-dd HH:mm:ss
         *
         * @param time 时间字符串
         * @return 友好型与当前时间的差
         *
         *  * 如果小于1秒钟内，显示刚刚
         *  * 如果在1分钟内，显示XXX秒前
         *  * 如果在1小时内，显示XXX分钟前
         *  * 如果在1小时外的今天内，显示今天15:32
         *  * 如果是昨天的，显示昨天15:32
         *  * 其余显示，2016-10-15
         *  * 时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007
         *
         */
        fun getFriendlyTimeSpanByNow(time: String?): String {
            return getFriendlyTimeSpanByNow(
                time,
                DEFAULT_PATTERN
            )
        }

        /**
         * 获取友好型与当前时间的差
         *
         * time格式为pattern
         *
         * @param time 时间字符串
         * @param pattern 时间格式
         * @return 友好型与当前时间的差
         *
         *  * 如果小于1秒钟内，显示刚刚
         *  * 如果在1分钟内，显示XXX秒前
         *  * 如果在1小时内，显示XXX分钟前
         *  * 如果在1小时外的今天内，显示今天15:32
         *  * 如果是昨天的，显示昨天15:32
         *  * 其余显示，2016-10-15
         *  * 时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007
         *
         */
        fun getFriendlyTimeSpanByNow(time: String?, pattern: String?): String {
            return getFriendlyTimeSpanByNow(
                string2Millis(
                    time,
                    pattern
                )
            )
        }

        /**
         * 获取友好型与当前时间的差
         *
         * @param date Date类型时间
         * @return 友好型与当前时间的差
         *
         *  * 如果小于1秒钟内，显示刚刚
         *  * 如果在1分钟内，显示XXX秒前
         *  * 如果在1小时内，显示XXX分钟前
         *  * 如果在1小时外的今天内，显示今天15:32
         *  * 如果是昨天的，显示昨天15:32
         *  * 其余显示，2016-10-15
         *  * 时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007
         *
         */
        fun getFriendlyTimeSpanByNow(date: Date): String {
            return getFriendlyTimeSpanByNow(date.time)
        }

        /**
         * 获取友好型与当前时间的差
         *
         * @param millis 毫秒时间戳
         * @return 友好型与当前时间的差
         *
         *  * 如果小于1秒钟内，显示刚刚
         *  * 如果在1分钟内，显示XXX秒前
         *  * 如果在1小时内，显示XXX分钟前
         *  * 如果在1小时外的今天内，显示今天15:32
         *  * 如果是昨天的，显示昨天15:32
         *  * 其余显示，2016-10-15
         *  * 时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007
         *
         */
        @SuppressLint("DefaultLocale")
        fun getFriendlyTimeSpanByNow(millis: Long): String {
            val now = System.currentTimeMillis()
            val span = now - millis
            if (span < 0) return String.format(
                "%tc",
                millis
            ) // U can read http://www.apihome.cn/api/java/Formatter.html to understand it.
            if (span < 1000) {
                return "刚刚"
            } else if (span < TimeConstants.MIN) {
                return String.format("%d秒前", span / TimeConstants.SEC)
            } else if (span < TimeConstants.HOUR) {
                return String.format("%d分钟前", span / TimeConstants.MIN)
            }
            // 获取当天00:00
            val wee =
                now / TimeConstants.DAY * TimeConstants.DAY - 8 * TimeConstants.HOUR
            return if (millis >= wee) {
                String.format("今天%tR", millis)
            } else if (millis >= wee - TimeConstants.DAY) {
                String.format("昨天%tR", millis)
            } else {
                String.format("%tF", millis)
            }
        }

        /**
         * 判断是否同一天
         *
         * time格式为yyyy-MM-dd HH:mm:ss
         *
         * @param time 时间字符串
         * @return `true`: 是<br></br>`false`: 否
         */
        fun isSameDay(time: String?): Boolean {
            return isSameDay(
                string2Millis(
                    time,
                    DEFAULT_PATTERN
                )
            )
        }

        /**
         * 判断是否同一天
         *
         * time格式为pattern
         *
         * @param time 时间字符串
         * @param pattern 时间格式
         * @return `true`: 是<br></br>`false`: 否
         */
        fun isSameDay(time: String?, pattern: String?): Boolean {
            return isSameDay(
                string2Millis(
                    time,
                    pattern
                )
            )
        }

        /**
         * 判断是否同一天
         *
         * @param date Date类型时间
         * @return `true`: 是<br></br>`false`: 否
         */
        fun isSameDay(date: Date): Boolean {
            return isSameDay(date.time)
        }

        /**
         * 判断是否同一天
         *
         * @param millis 毫秒时间戳
         * @return `true`: 是<br></br>`false`: 否
         */
        fun isSameDay(millis: Long): Boolean {
            val wee =
                System.currentTimeMillis() / TimeConstants.DAY * TimeConstants.DAY - 8 * TimeConstants.HOUR
            return millis >= wee && millis < wee + TimeConstants.DAY
        }

        /**
         * 判断是否闰年
         *
         * time格式为yyyy-MM-dd HH:mm:ss
         *
         * @param time 时间字符串
         * @return `true`: 闰年<br></br>`false`: 平年
         */
        fun isLeapYear(time: String?): Boolean {
            return isLeapYear(
                string2Date(
                    time,
                    DEFAULT_PATTERN
                )
            )
        }

        /**
         * 判断是否闰年
         *
         * time格式为pattern
         *
         * @param time 时间字符串
         * @param pattern 时间格式
         * @return `true`: 闰年<br></br>`false`: 平年
         */
        fun isLeapYear(time: String?, pattern: String?): Boolean {
            return isLeapYear(
                string2Date(
                    time,
                    pattern
                )
            )
        }

        /**
         * 判断是否闰年
         *
         * @param date Date类型时间
         * @return `true`: 闰年<br></br>`false`: 平年
         */
        fun isLeapYear(date: Date?): Boolean {
            val cal = Calendar.getInstance()
            cal.time = date
            val year = cal[Calendar.YEAR]
            return isLeapYear(year)
        }

        /**
         * 判断是否闰年
         *
         * @param millis 毫秒时间戳
         * @return `true`: 闰年<br></br>`false`: 平年
         */
        fun isLeapYear(millis: Long): Boolean {
            return isLeapYear(
                millis2Date(
                    millis
                )
            )
        }

        /**
         * 判断是否闰年
         *
         * @param year 年份
         * @return `true`: 闰年<br></br>`false`: 平年
         */
        fun isLeapYear(year: Int): Boolean {
            return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
        }

        /**
         * 获取星期
         *
         * time格式为yyyy-MM-dd HH:mm:ss
         *
         * @param time 时间字符串
         * @return 星期
         */
        fun getWeek(time: String?): String {
            return getWeek(
                string2Date(
                    time,
                    DEFAULT_PATTERN
                )
            )
        }

        /**
         * 获取星期
         *
         * time格式为pattern
         *
         * @param time 时间字符串
         * @param pattern 时间格式
         * @return 星期
         */
        fun getWeek(time: String?, pattern: String?): String {
            return getWeek(
                string2Date(
                    time,
                    pattern
                )
            )
        }

        /**
         * 获取星期
         *
         * @param date Date类型时间
         * @return 星期
         */
        fun getWeek(date: Date?): String {
            return SimpleDateFormat("EEEE", Locale.getDefault()).format(date)
        }

        /**
         * 获取星期
         *
         * @param millis 毫秒时间戳
         * @return 星期
         */
        fun getWeek(millis: Long): String {
            return getWeek(Date(millis))
        }

        /**
         * 获取星期
         *
         * 注意：周日的Index才是1，周六为7
         *
         * time格式为yyyy-MM-dd HH:mm:ss
         *
         * @param time 时间字符串
         * @return 1...5
         */
        fun getWeekIndex(time: String?): Int {
            return getWeekIndex(
                string2Date(
                    time,
                    DEFAULT_PATTERN
                )
            )
        }

        /**
         * 获取星期
         *
         * 注意：周日的Index才是1，周六为7
         *
         * time格式为pattern
         *
         * @param time 时间字符串
         * @param pattern 时间格式
         * @return 1...7
         */
        fun getWeekIndex(time: String?, pattern: String?): Int {
            return getWeekIndex(
                string2Date(
                    time,
                    pattern
                )
            )
        }

        /**
         * 获取星期
         *
         * 注意：周日的Index才是1，周六为7
         *
         * @param date Date类型时间
         * @return 1...7
         */
        fun getWeekIndex(date: Date?): Int {
            val cal = Calendar.getInstance()
            cal.time = date
            return cal[Calendar.DAY_OF_WEEK]
        }

        /**
         * 获取星期
         *
         * 注意：周日的Index才是1，周六为7
         *
         * @param millis 毫秒时间戳
         * @return 1...7
         */
        fun getWeekIndex(millis: Long): Int {
            return getWeekIndex(
                millis2Date(
                    millis
                )
            )
        }

        /**
         * 获取月份中的第几周
         *
         * 注意：国外周日才是新的一周的开始
         *
         * time格式为yyyy-MM-dd HH:mm:ss
         *
         * @param time 时间字符串
         * @return 1...5
         */
        fun getWeekOfMonth(time: String?): Int {
            return getWeekOfMonth(
                string2Date(
                    time,
                    DEFAULT_PATTERN
                )
            )
        }

        /**
         * 获取月份中的第几周
         *
         * 注意：国外周日才是新的一周的开始
         *
         * time格式为pattern
         *
         * @param time 时间字符串
         * @param pattern 时间格式
         * @return 1...5
         */
        fun getWeekOfMonth(time: String?, pattern: String?): Int {
            return getWeekOfMonth(
                string2Date(
                    time,
                    pattern
                )
            )
        }

        /**
         * 获取月份中的第几周
         *
         * 注意：国外周日才是新的一周的开始
         *
         * @param date Date类型时间
         * @return 1...5
         */
        fun getWeekOfMonth(date: Date?): Int {
            val cal = Calendar.getInstance()
            cal.time = date
            return cal[Calendar.WEEK_OF_MONTH]
        }

        /**
         * 获取月份中的第几周
         *
         * 注意：国外周日才是新的一周的开始
         *
         * @param millis 毫秒时间戳
         * @return 1...5
         */
        fun getWeekOfMonth(millis: Long): Int {
            return getWeekOfMonth(
                millis2Date(
                    millis
                )
            )
        }

        /**
         * 获取年份中的第几周
         *
         * 注意：国外周日才是新的一周的开始
         *
         * time格式为yyyy-MM-dd HH:mm:ss
         *
         * @param time 时间字符串
         * @return 1...54
         */
        fun getWeekOfYear(time: String?): Int {
            return getWeekOfYear(
                string2Date(
                    time,
                    DEFAULT_PATTERN
                )
            )
        }

        /**
         * 获取年份中的第几周
         *
         * 注意：国外周日才是新的一周的开始
         *
         * time格式为pattern
         *
         * @param time 时间字符串
         * @param pattern 时间格式
         * @return 1...54
         */
        fun getWeekOfYear(time: String?, pattern: String?): Int {
            return getWeekOfYear(
                string2Date(
                    time,
                    pattern
                )
            )
        }

        /**
         * 获取年份中的第几周
         *
         * 注意：国外周日才是新的一周的开始
         *
         * @param date Date类型时间
         * @return 1...54
         */
        fun getWeekOfYear(date: Date?): Int {
            val cal = Calendar.getInstance()
            cal.time = date
            return cal[Calendar.WEEK_OF_YEAR]
        }

        /**
         * 获取年份中的第几月
         *
         * @param date Date类型时间
         * @return 1...54
         */
        fun getMonthOfYear(date: Date?): Int {
            return try {
                val cal = Calendar.getInstance()
                cal.time = date
                cal[Calendar.MONTH] + 1
            } catch (e: Exception) {
                e.printStackTrace()
                -1
            }
        }

        /**
         * 获取年
         *
         * @param date
         * @return
         */
        fun getYear(date: Date?): Int {
            val cal = Calendar.getInstance()
            cal.time = date
            return cal[Calendar.YEAR]
        }

        /**
         * 获取月
         *
         * @param date
         * @return
         */
        fun getMonth(date: Date?): Int {
            val cal = Calendar.getInstance()
            cal.time = date
            return cal[Calendar.MONTH] + 1
        }

        /**
         * 获取月
         *
         * @param date
         * @return
         */
        fun getDay(date: Date?): Int {
            val cal = Calendar.getInstance()
            cal.time = date
            return cal[Calendar.DATE]
        }

        /**
         * 获取年份中的第几周
         *
         * 注意：国外周日才是新的一周的开始
         *
         * @param millis 毫秒时间戳
         * @return 1...54
         */
        fun getWeekOfYear(millis: Long): Int {
            return getWeekOfYear(
                millis2Date(
                    millis
                )
            )
        }

        private val CHINESE_ZODIAC =
            arrayOf("猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊")

        /**
         * 获取生肖
         *
         * time格式为yyyy-MM-dd HH:mm:ss
         *
         * @param time 时间字符串
         * @return 生肖
         */
        fun getChineseZodiac(time: String?): String {
            return getChineseZodiac(
                string2Date(
                    time,
                    DEFAULT_PATTERN
                )
            )
        }

        /**
         * 获取生肖
         *
         * time格式为pattern
         *
         * @param time 时间字符串
         * @param pattern 时间格式
         * @return 生肖
         */
        fun getChineseZodiac(time: String?, pattern: String?): String {
            return getChineseZodiac(
                string2Date(
                    time,
                    pattern
                )
            )
        }

        /**
         * 获取生肖
         *
         * @param date Date类型时间
         * @return 生肖
         */
        fun getChineseZodiac(date: Date?): String {
            val cal = Calendar.getInstance()
            cal.time = date
            return CHINESE_ZODIAC[cal[Calendar.YEAR] % 12]
        }

        /**
         * 获取生肖
         *
         * @param millis 毫秒时间戳
         * @return 生肖
         */
        fun getChineseZodiac(millis: Long): String {
            return getChineseZodiac(
                millis2Date(
                    millis
                )
            )
        }

        /**
         * 获取生肖
         *
         * @param year 年
         * @return 生肖
         */
        fun getChineseZodiac(year: Int): String {
            return CHINESE_ZODIAC[year % 12]
        }

        private val ZODIAC = arrayOf(
            "水瓶座",
            "双鱼座",
            "白羊座",
            "金牛座",
            "双子座",
            "巨蟹座",
            "狮子座",
            "处女座",
            "天秤座",
            "天蝎座",
            "射手座",
            "魔羯座"
        )
        private val ZODIAC_FLAGS =
            intArrayOf(20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22)

        /**
         * 获取星座
         *
         * time格式为yyyy-MM-dd HH:mm:ss
         *
         * @param time 时间字符串
         * @return 生肖
         */
        fun getZodiac(time: String?): String {
            return getZodiac(
                string2Date(
                    time,
                    DEFAULT_PATTERN
                )
            )
        }

        /**
         * 获取星座
         *
         * time格式为pattern
         *
         * @param time 时间字符串
         * @param pattern 时间格式
         * @return 生肖
         */
        fun getZodiac(time: String?, pattern: String?): String {
            return getZodiac(
                string2Date(
                    time,
                    pattern
                )
            )
        }

        /**
         * 获取星座
         *
         * @param date Date类型时间
         * @return 星座
         */
        fun getZodiac(date: Date?): String {
            val cal = Calendar.getInstance()
            cal.time = date
            val month = cal[Calendar.MONTH] + 1
            val day = cal[Calendar.DAY_OF_MONTH]
            return getZodiac(month, day)
        }

        /**
         * 获取星座
         *
         * @param millis 毫秒时间戳
         * @return 星座
         */
        fun getZodiac(millis: Long): String {
            return getZodiac(
                millis2Date(
                    millis
                )
            )
        }

        /**
         * 获取星座
         *
         * @param month 月
         * @param day 日
         * @return 星座
         */
        fun getZodiac(month: Int, day: Int): String {
            return ZODIAC[if (day >= ZODIAC_FLAGS[month - 1]
            ) month - 1 else (month + 10) % 12]
        }

        /**
         * 获取时间节点描述
         *
         * @return
         */
        val dateHello: String
            get() {
                val cal = Calendar.getInstance()
                val hour = cal[Calendar.HOUR_OF_DAY]
                return if (hour >= 6 && hour < 8) {
                    "早上好!"
                } else if (hour >= 8 && hour < 11) {
                    "上午好!"
                } else if (hour >= 11 && hour < 13) {
                    "中午好!"
                } else if (hour >= 13 && hour < 18) {
                    "下午好!"
                } else {
                    "晚上好!"
                }
            }

        /**
         * 获取时间差
         *
         * @param date2
         * @param date1
         * @return
         */
        fun getDateCount(date2: Long, date1: Long): IntArray {
            val list = IntArray(4)
            val l = date2 - date1
            val day = (l / (24 * 60 * 60 * 1000)).toInt()
            val hour = (l / (60 * 60 * 1000) - day * 24).toInt()
            val min = (l / (60 * 1000) - day * 24 * 60 - hour * 60).toInt()
            val s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60).toInt()
            list[0] = day
            list[1] = hour
            list[2] = min
            list[3] = s
            return list
        }

        /**
         * 获取当前时间所在年的周数
         * @param date
         * @return
         */
        fun getWeeksOfYear(date: Date?): Int {
            val c: Calendar = GregorianCalendar()
            c.firstDayOfWeek = Calendar.MONDAY
            c.minimalDaysInFirstWeek = 7
            c.time = date
            return c[Calendar.WEEK_OF_YEAR]
        }

        /**
         * 格式化时间将
         * "yyyy-MM-dd HH:mm:ss";
         * 转为
         * "yyyy-MM-dd HH:mm";
         *
         * @param t
         * @return
         */
        fun formatTime(t: String?): String {
            var time = ""
            try {
                val date = string2Date(t)
                time = date2String(
                    date,
                    DEFAULT_PATTERN1
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return time
        }

        /**
         * 格式化时间将
         * "yyyy-MM-dd HH:mm:ss";
         * 转为
         * "yyyy-MM-dd;
         *
         * @param t
         * @return
         */
        fun formatTimeDay(t: String?): String {
            var time = ""
            try {
                val date = string2Date(t)
                time = date2String(
                    date,
                    DEFAULT_PATTERN2
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return time
        }

        /**
         * 格式化时间将
         * "yyyy-MM-dd HH:mm:ss";
         * 转为
         * "yyyy.MM.dd;
         *
         * @param t
         * @return
         */
        fun formatTimeDay1(t: String?): String {
            var time = ""
            try {
                val date = string2Date(t)
                time = date2String(
                    date,
                    DEFAULT_PATTERN3
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return time
        }

        /**
         * 格式化时间将
         * "yyyy-MM-dd HH:mm:ss";
         * 转为
         * 制定的格式;
         *
         * @param t
         * @return
         */
        fun formatTimeDay2(t: String?, pattern: String?): String {
            var time = ""
            try {
                val date = string2Date(t)
                time = date2String(date, pattern)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return time
        }

        /**
         * 将秒格式化成 xx:xx:xx
         *
         * @param time
         * @return
         */
        fun secToTime(time: Int): String {
            var timeStr: String? = null
            var hour = 0
            var minute = 0
            var second = 0
            if (time <= 0) return "00:00:00" else {
                minute = time / 60
                if (minute < 60) {
                    second = time % 60
                    timeStr =
                        "00:" + unitFormat(minute) + ":" + unitFormat(
                            second
                        )
                } else {
                    hour = minute / 60
                    if (hour > 99) return "99:59:59"
                    minute = minute % 60
                    second = time - hour * 3600 - minute * 60
                    timeStr =
                        unitFormat(hour) + ":" + unitFormat(
                            minute
                        ) + ":" + unitFormat(second)
                }
            }
            return timeStr
        }

        fun unitFormat(i: Int): String {
            var retStr: String? = null
            retStr = if (i >= 0 && i < 10) "0" + Integer.toString(i) else "" + i
            return retStr
        }

        /**
         * 获取两个日期之间的日期
         * @param start 开始日期
         * @param end 结束日期
         * @return 日期集合
         */
        fun getBetweenDates(
            start: Date?,
            end: Date?
        ): List<Date> {
            val result: MutableList<Date> =
                ArrayList()
            val tempStart = Calendar.getInstance()
            tempStart.time = start
            tempStart.add(Calendar.DAY_OF_YEAR, 1)
            val tempEnd = Calendar.getInstance()
            tempEnd.time = end
            while (tempStart.before(tempEnd)) {
                result.add(tempStart.time)
                tempStart.add(Calendar.DAY_OF_YEAR, 1)
            }
            return result
        }

        /**
         * 毫秒时间戳转以unit为单位的时间长度
         *
         * @param millis 毫秒时间戳
         * @param unit   单位类型
         *
         *  * [TimeConstants.MSEC]: 毫秒
         *  * [TimeConstants.SEC]: 秒
         *  * [TimeConstants.MIN]: 分
         *  * [TimeConstants.HOUR]: 小时
         *  * [TimeConstants.DAY]: 天
         *
         * @return 以unit为单位的时间长度
         */
        fun millis2TimeSpan(millis: Long, @TimeConstants.Unit unit: Int): Long {
            return millis / unit
        }

        /**
         * 毫秒时间戳转合适时间长度
         *
         * @param millis    毫秒时间戳
         *
         * 小于等于0，返回null
         * @param precision 精度
         *
         *  * precision = 0，返回null
         *  * precision = 1，返回天
         *  * precision = 2，返回天和小时
         *  * precision = 3，返回天、小时和分钟
         *  * precision = 4，返回天、小时、分钟和秒
         *  * precision &gt;= 5，返回天、小时、分钟、秒和毫秒
         *
         * @return 合适时间长度
         */
        @SuppressLint("DefaultLocale")
        fun millis2FitTimeSpan(millis: Long, precision: Int): String? {
            var millis = millis
            var precision = precision
            if (millis <= 0 || precision <= 0) return null
            val sb = StringBuilder()
            val units =
                arrayOf("天", "小时", "分钟", "秒", "毫秒")
            val unitLen = intArrayOf(86400000, 3600000, 60000, 1000, 1)
            precision = Math.min(precision, 5)
            for (i in 0 until precision) {
                if (millis >= unitLen[i]) {
                    val mode = millis / unitLen[i]
                    millis -= mode * unitLen[i]
                    sb.append(mode).append(units[i])
                }
            }
            return sb.toString()
        }

        /**
         * 获取前n天日期、后n天日期
         * @param beginDate 开始日期
         * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
         * @return
         */
        fun getOldDate(beginDate: Date?, distanceDay: Int): String {
            val dft = SimpleDateFormat("yyyy-MM-dd")
            val date = Calendar.getInstance()
            date.time = beginDate
            date[Calendar.DATE] = date[Calendar.DATE] + distanceDay
            var endDate: Date? = null
            try {
                endDate = dft.parse(dft.format(date.time))
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return dft.format(endDate)
        }
    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }
}