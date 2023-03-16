package ru.logging.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Logging(
    val projectName: String,
    val url: String? = null,
    val methodName: String? = null,
    val className: String? = null,
    val typeMessage: RequestResponse? = null,
    val requestResponse: String? = null,
    val error: String? = null
)