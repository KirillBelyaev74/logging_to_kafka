package ru.logging.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.*
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.logging.model.ObjectToStringForLog
import ru.logging.model.TypeMessage

@Aspect
@Component
open class LogAspect {

    @Autowired
    private lateinit var logAspectGeneral: LogAspectGeneral

    @Pointcut("@annotation(ru.logging.annotation.Log)")
    fun aspectLog() {
    }

    @Before("aspectLog()")
    fun beforeLog(jp: JoinPoint) {
        val methodSignature = jp.signature as MethodSignature
        val parameterAnnotations = methodSignature.method.parameterAnnotations
        val args: MutableList<Any> = ArrayList()
        parameterAnnotations.forEachIndexed { i, any ->
            if (any.isEmpty()) args.add(jp.args[i])
        }
        val requestResponse = jp.args.joinToString(prefix = "[", postfix = "]")
        logAspectGeneral.log(jp, TypeMessage.REQUEST, requestResponse)
    }

    @AfterReturning(pointcut = "aspectLog()", returning = "response")
    fun afterLogOk(jp: JoinPoint, response: Any) {
        val requestResponse = when (response) {
//            is Collection<*> -> "size: ${response.size}"
//            is Map<*, *> -> "size: ${response.size}"
            is ObjectToStringForLog -> response.objectToString()
            else -> ""
        }
        logAspectGeneral.log(jp, TypeMessage.RESPONSE, requestResponse)
    }

    @AfterThrowing(pointcut = "aspectLog()", throwing = "exception")
    fun afterLogError(jp: JoinPoint, exception: Exception) {
        logAspectGeneral.logError(jp, exception)
    }
}

