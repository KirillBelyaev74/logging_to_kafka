package ru.logging.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import ru.logging.dropNamePackage
import ru.logging.model.Logging
import ru.logging.model.LoggingEvent
import ru.logging.model.TypeMessage

@Component
open class LogAspectGeneral {

    @Autowired
    private lateinit var eventPublisher: ApplicationEventPublisher

    @Value("\${spring.kafka.topic.name}")
    private lateinit var projectName: String

    fun log(jp: JoinPoint, requestResponse: TypeMessage, body: String) {
        val methodSignature = jp.signature as MethodSignature
        val log = Logging(
            projectName = projectName,
            className = methodSignature.declaringTypeName.dropNamePackage(),
            methodName = methodSignature.name,
            typeMessage = requestResponse,
            requestResponse = body
        )
        eventPublisher.publishEvent(LoggingEvent(this, log))
    }

    fun logError(jp: JoinPoint, exception: Exception) {
        val methodSignature = jp.signature as MethodSignature
        val log = Logging(
            projectName = projectName,
            className = methodSignature.declaringTypeName.dropNamePackage(),
            methodName = methodSignature.name,
            error = exception.message
        )
        eventPublisher.publishEvent(LoggingEvent(this, log))
    }
}