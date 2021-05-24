package com.zh.common.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.LocaleList
import android.text.TextUtils
import com.blankj.utilcode.util.SPUtils
import com.zh.config.ZjConfig
import java.util.*

class LanguageUtil {

    /**
     * 更改应用语言
     *
     * @param context     放在activity
     * @param locale      语言地区
     * @param persistence 是否持久化
     */
    fun changeAppLanguage(context: Context, locale: Locale, persistence: Boolean) {
        var locale = locale
        if (TextUtils.isEmpty(SPUtils.getInstance().getString(ZjConfig.LANGUAGE))) {
            locale = Locale.SIMPLIFIED_CHINESE
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
        type?.let { SPUtils.getInstance().put(ZjConfig.LANGUAGE, it) }
    }

    fun getLanguageSetting(): Locale {
        when {
            SPUtils.getInstance().getString(ZjConfig.LANGUAGE).equals(ZjConfig.lag_zh_CN) -> {
                return Locale.SIMPLIFIED_CHINESE
            }
            SPUtils.getInstance().getString(ZjConfig.LANGUAGE).equals(ZjConfig.lag_en) -> {
                return Locale.ENGLISH
            }
            SPUtils.getInstance().getString(ZjConfig.LANGUAGE).equals(ZjConfig.lag_it_IT) -> {
                return Locale.ITALY
            }
            SPUtils.getInstance().getString(ZjConfig.LANGUAGE).equals(ZjConfig.lag_ge) -> {
                return Locale.GERMAN
            }
            SPUtils.getInstance().getString(ZjConfig.LANGUAGE).equals(ZjConfig.lag_gr) -> {
                return Locale("el", "GR")
            }
            SPUtils.getInstance().getString(ZjConfig.LANGUAGE).equals(ZjConfig.lag_fr) -> {
                return Locale.FRANCE
            }
            SPUtils.getInstance().getString(ZjConfig.LANGUAGE).equals(ZjConfig.lag_es) -> {
                return Locale("es", "ES")
            }
            else -> return Locale.SIMPLIFIED_CHINESE
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