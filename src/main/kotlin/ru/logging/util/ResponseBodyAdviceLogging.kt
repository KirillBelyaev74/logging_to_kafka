package ru.logging.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import ru.logging.model.Logging
import ru.logging.model.LoggingEvent
import ru.logging.model.TypeMessage

@RestControllerAdvice
open class ResponseBodyAdviceLogging<R>: ResponseBodyAdvice<R> {

    @Autowired
    private lateinit var eventPublisher: ApplicationEventPublisher

    @Value("\${spring.kafka.topic.name}")
    private lateinit var projectName: String

    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        return true
    }

    override fun beforeBodyWrite(
        body: R?,
        methodParameter: MethodParameter,
        mediaType: MediaType,
        converterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): R? {
        if (!methodParameter.method.name.contains("xception")) {
            val log = Logging(
                projectName = projectName,
                url = request.uri.path,
                methodName = request.method?.name,
                typeMessage = TypeMessage.RESPONSE,
                requestResponse = if (body != null && body is List<*>) "size ${body.size}" else body.toString()
            )
            eventPublisher.publishEvent(LoggingEvent(this, log))
        }
        return body
    }
}