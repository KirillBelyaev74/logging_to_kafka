package ru.logging.model

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Logging(
        var date: String? = null,
        val projectName: String,
        val url: String? = null,
        val methodName: String? = null,
        val className: String? = null,
        val typeMessage: TypeMessage? = null,
        val requestResponse: String? = null,
        val error: String? = null
) {

    fun setLocalDateTimeFormatter(): Logging {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        date = LocalDateTime.now().format(formatter)
        return this
    }
}