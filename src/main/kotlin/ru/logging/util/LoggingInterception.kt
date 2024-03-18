package ru.logging.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import ru.logging.model.Logging
import ru.logging.model.TypeMessage
import ru.logging.service.KafkaProducerService
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
open class LoggingInterception : HandlerInterceptor {

    @Autowired
    private lateinit var kafkaProducer: KafkaProducerService

    @Value("\${spring.kafka.topic.name}")
    private lateinit var projectName: String

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.method == HttpMethod.GET.name && handler is HandlerMethod && !request.requestURI.contains("/error")) {
            val log = Logging(
                projectName = projectName,
                url = request.requestURI,
                methodName = request.method,
                typeMessage = TypeMessage.REQUEST
            )
            kafkaProducer.sendLog(log)
        }
        return true
    }
}