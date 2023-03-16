package ru.logging.model

import org.springframework.context.ApplicationEvent

class LoggingEvent(source: Any, logging: Logging): ApplicationEvent(source) {

    val logging: Logging = logging
        get() {
            return field
        }
}