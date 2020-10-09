package com.zh.common.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.LocaleList
import android.text.TextUtils
import com.zh.config.ZjConfig
import java.util.*

class LanguageUtil private constructor() {

    //单列
    companion object {
        private var instance: LanguageUtil? = null
            get() {
                if (field == null) field = LanguageUtil()
                return field
            }

        @Synchronized
        fun get(): LanguageUtil = instance!!
    }

    /**
     * 更改应用语言
     *
     * @param context     放在activity
     * @param locale      语言地区
     * @param persistence 是否持久化
     */
    fun changeAppLanguage(context: Context, locale: Locale, persistence: Boolean) {
        var locale = locale
        if (TextUtils.isEmpty(SpUtil.getStringSF(ZjConfig.LANGUAGE))) {
            locale = Locale.ENGLISH
            saveLanguageSetting(locale)
        }
        val resources = context.applicationContext.resources
        val metrics = resources.displayMetrics
        val configuration = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale)
        } else {
            configuration.locale = locale
        }
        resources.updateConfiguration(configuration, metrics)
        if (persistence) {
            saveLanguageSetting(locale)
        }
    }

    //Locale.ENGLISH、Locale.TRADITIONAL_CHINESE、Locale.SIMPLIFIED_CHINESE
    //en、zh_TW、zh_CN
    private fun saveLanguageSetting(locale: Locale) {
        var type: String? = ZjConfig.lag_zh_CN
        when {
            locale === Locale.SIMPLIFIED_CHINESE -> {
                type = ZjConfig.lag_zh_CN
            }
            locale === Locale.ENGLISH -> {
                type = ZjConfig.lag_en
            }
            locale === Locale.ITALY -> {
                type = ZjConfig.lag_it_IT
            }
            locale === Locale.GERMAN -> {
                type = ZjConfig.lag_ge
            }
            locale == Locale("el", "GR") -> {
                type = ZjConfig.lag_gr
            }
            locale === Locale.FRANCE -> {
                type = ZjConfig.lag_fr
            }
            locale == Locale("es", "ES") -> {
                type = ZjConfig.lag_es
            }
        }
        type?.let { SpUtil.setStringSF(ZjConfig.LANGUAGE, it) }
    }

    fun getLanguageSetting(): Locale {
        when {
            SpUtil.getStringSF(ZjConfig.LANGUAGE).equals(ZjConfig.lag_zh_CN) -> {
                return Locale.SIMPLIFIED_CHINESE
            }
            SpUtil.getStringSF(ZjConfig.LANGUAGE).equals(ZjConfig.lag_en) -> {
                return Locale.ENGLISH
            }
            SpUtil.getStringSF(ZjConfig.LANGUAGE).equals(ZjConfig.lag_it_IT) -> {
                return Locale.ITALY
            }
            SpUtil.getStringSF(ZjConfig.LANGUAGE).equals(ZjConfig.lag_ge) -> {
                return Locale.GERMAN
            }
            SpUtil.getStringSF(ZjConfig.LANGUAGE).equals(ZjConfig.lag_gr) -> {
                return Locale("el", "GR")
            }
            SpUtil.getStringSF(ZjConfig.LANGUAGE).equals(ZjConfig.lag_fr) -> {
                return Locale.FRANCE
            }
            SpUtil.getStringSF(ZjConfig.LANGUAGE).equals(ZjConfig.lag_es) -> {
                return Locale("es", "ES")
            }
            else -> return Locale.ENGLISH
        }
    }

    fun attachBaseContext(context: Context): Context? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // 8.0需要使用createConfigurationContext处理
            updateResources(context)
        } else {
            context
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context): Context? {
        val resources = context.resources
        val locale = getLanguageSetting() // getSetLocale方法是获取新设置的语言
        val configuration = resources.configuration
        configuration.setLocale(locale)
        configuration.setLocales(LocaleList(locale))
        return context.createConfigurationContext(configuration)
    }
}