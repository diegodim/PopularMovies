package com.diego.duarte.popularmovieskotlin.util

import java.text.SimpleDateFormat
import java.util.*

class Util {

    fun formateDate(date: Date?): String {
        return if(date != null)
            SimpleDateFormat("dd/MM/yyyy", Locale("pt-br", "America/Sao_Paulo"))
                .format(date)
        else
            ""
    }
}