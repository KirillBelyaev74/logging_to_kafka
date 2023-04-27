package ru.logging.model

import ru.logging.annotation.LogFieldSkip

class ObjectToStringForLog {

    fun objectToString(): String {
        return this::class.java.declaredFields
            .filter { !it.isEnumConstant }
            .filter { !it.isAnnotationPresent(LogFieldSkip::class.java) }
            .map {
                it.isAccessible = true
                when (val any = it.get(this)) {
                    is Collection<*> -> any.size.toString()
                    is Map<*, *> -> any.size.toString()
                    else -> "${it.name}: $it"
                }
            }.toString()
    }
}