package com.ahmed.areebmovies.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeHelper {
    private const val DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd"
    private const val DATE_FORMAT_YYYY_MMM = "yyyy, MMM"

    fun convertDateStringToAnotherFormat(
        dateString: String?,
        dateParserFormat: String = DATE_FORMAT_YYYY_MM_DD,
        dateFormatter: String = DATE_FORMAT_YYYY_MMM,
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

    fun getDurationPair(duration: Int?): Pair<Int, Int> =
        Pair((duration ?: 0) / 60, (duration ?: 0) % 60)

}