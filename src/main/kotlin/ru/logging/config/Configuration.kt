package ru.logging.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.logging.aop.LogAspectGeneral
import ru.logging.aop.LogAspectService
import ru.logging.service.LoggingListener
import ru.logging.util.GlobalRestControllerExceptionHandler
import ru.logging.util.LoggingInterception
import ru.logging.util.RequestBodyAdviceLogging
import ru.logging.util.ResponseBodyAdviceLogging

@Configuration
open class Configuration {

    @Bean
    open fun logAspectService(): LogAspectService {
        return LogAspectService()
    }

    @Bean
    open fun logAspectGeneral(): LogAspectGeneral {
        return LogAspectGeneral()
    }

    @Bean
    open fun loggingListener(): LoggingListener {
        return LoggingListener();
    }

    @Bean
    open fun globalRestControllerExceptionHandler(): GlobalRestControllerExceptionHandler {
        return GlobalRestControllerExceptionHandler();
    }

    @Bean
    open fun loggingInterception(): LoggingInterception {
        return LoggingInterception();
    }

    @Bean
    open fun requestBodyAdviceLogging(): RequestBodyAdviceLogging {
        return RequestBodyAdviceLogging();
    }

    @Bean
    open fun <R> responseBodyAdviceLogging(): ResponseBodyAdviceLogging<R> {
        return ResponseBodyAdviceLogging<R>();
    }
}