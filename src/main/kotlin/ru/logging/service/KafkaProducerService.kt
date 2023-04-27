package ru.logging.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.logging.model.Logging

@Service
class KafkaProducerService(private val kafkaTemplate: KafkaTemplate<String, String>) {

    @Value("\${spring.kafka.topic.name}")
    private lateinit var topicName: String

    fun sendLog(logging: Logging) {
        kafkaTemplate.send(topicName, logging.toString())
    }
}