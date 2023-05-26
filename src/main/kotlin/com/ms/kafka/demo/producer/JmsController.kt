package com.ms.kafka.demo.producer

import com.ms.kafka.demo.SystemMessage
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

/**
 * @author Ivelin Dimitrov
 */

@RestController
class JmsController(
    val jmsProducer: JmsProducer
) {
    private val log = LoggerFactory.getLogger(JmsController::class.java)

    @GetMapping("/trigger/{id}")
    fun triggerEvents(
        @PathVariable id: Int
    ) {
        log.info("Received: $id")
        jmsProducer.send(
            message = SystemMessage(
                source = "postman",
                message = id.toString()
            )
        )
    }
}