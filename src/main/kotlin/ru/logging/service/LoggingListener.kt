package ru.logging.service

import org.springframework.context.ApplicationListener
import ru.logging.model.LoggingEvent

open class LoggingListener : ApplicationListener<LoggingEvent> {

    override fun onApplicationEvent(event: LoggingEvent) {
        println(event.logging)
    }
}