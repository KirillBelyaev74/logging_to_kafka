package ru.logging.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import ru.logging.model.Logging
import ru.logging.service.KafkaProducerService
import javax.servlet.http.HttpServletRequest

@ControllerAdvice(annotations = [RestController::class])
open class GlobalRestControllerExceptionHandler {

    @Autowired
    private lateinit var kafkaProducer: KafkaProducerService

    @Autowired
    private lateinit var request: HttpServletRequest

    @Value("\${spring.kafka.topic.name}")
    private lateinit var projectName: String

    @ExceptionHandler(Exception::class)
    fun handlerException(e: Exception): ResponseEntity<Map<String, String?>> {
        println(e.stackTraceToString())
        val log = Logging(
            projectName = projectName,
            url = request.requestURI,
            methodName = request.method,
            error = e.message
        )
        kafkaProducer.sendLog(log)
        val response = mapOf("error" to e.message)
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}