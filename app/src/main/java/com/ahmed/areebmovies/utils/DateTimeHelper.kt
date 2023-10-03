package com.ahmed.areebmovies.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeHelper {
    private const val DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd"
    private const val DATE_FORMAT_YYYY_MM_DD_T_ZEROS = "yyyy-MM-dd'T'00:00:00'Z'"
    private const val DATE_FORMAT_d_MMMM_YYYY = "d MMMM yyyy"
    const val DATE_FORMAT_E_d_MMM = "E d, MMM"
    const val DATE_FORMAT_YYYY = "yyyy"
    private const val DATE_FORMAT_HH_MM = "HH:mm"

    fun convertDateStringToAnotherFormat(
        dateString: String?,
        dateParserFormat: String = DATE_FORMAT_YYYY_MM_DD,
        dateFormatter: String = DATE_FORMAT_YYYY,
        desiredLocale: Locale = Locale.getDefault(),
        alternateValue: String? = Constants.General.DASH_TEXT
    ): String? {
        if (dateString.isNullOrEmpty()) {
            return alternateValue
        }
        val parser = SimpleDateFormat(dateParserFormat, desiredLocale)
        val formatter = SimpleDateFormat(dateFormatter, desiredLocale)
        try {
            return formatter.format(parser.parse(dateString)!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return alternateValue
    }






}