package com.raquelbytes.grapeguard.Util

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.Locale

object LocaleUtil {
    private const val PREFS_NAME = "locale_prefs"
    private const val KEY_LANGUAGE = "language"

    fun setLocale(context: Context, language: String) {
        persistLanguage(context, language)

        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources: Resources = context.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    fun getCurrentLocale(context: Context): String {
        val config: Configuration = context.resources.configuration
        val locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.locales[0]
        } else {
            config.locale
        }
        return locale.language
    }

    private fun persistLanguage(context: Context, language: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(KEY_LANGUAGE, language)
        editor.apply()
    }

    fun loadLocale(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val language: String = prefs.getString(KEY_LANGUAGE, "es") ?: "es"
        setLocale(context, language)
    }
}
