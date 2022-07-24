package com.annti.surfgallery.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import java.text.SimpleDateFormat
import java.util.*

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun dateFormat(oldStringDate: Long): String =
    SimpleDateFormat("dd.MM.yyyy", Locale(getCountry())).format(Date(oldStringDate))

private fun getCountry(): String =
    Locale.getDefault().country.lowercase(Locale.ROOT)