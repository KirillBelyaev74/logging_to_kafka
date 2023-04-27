package ru.logging.service

import org.springframework.context.ApplicationListener
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.logging.model.LoggingEvent

@Component
open class LoggingListener : ApplicationListener<LoggingEvent> {

    @EventListener
    override fun onApplicationEvent(event: LoggingEvent) {
        println(event.logging)
    }
}