package com.ms.kafka.demo.producer

import com.ms.kafka.demo.SystemMessage
import org.slf4j.LoggerFactory
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Component

/**
 * @author Ivelin Dimitrov
 */
@Component
class JmsProducer(
    val jmsTemplate: JmsTemplate
) {
    private val log = LoggerFactory.getLogger(JmsProducer::class.java)

    fun send(topic: String = "trigger", message: SystemMessage) {
        jmsTemplate.convertAndSend(
            message = message,
            destinationName = topic
        )
    }
}

private fun JmsTemplate.convertAndSend(message: SystemMessage, destinationName: String) {
    return convertAndSend(destinationName, message)
}
