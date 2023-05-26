package com.ms.kafka.demo.consumer

import com.ms.kafka.demo.SystemMessage
import com.ms.kafka.demo.producer.JmsProducer
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

/**
 * @author Ivelin Dimitrov
 */
@Component
class JmsConsumer(
    private val jmsProducer: JmsProducer,
    private val restTemplate: RestTemplate
) {
    private val log = LoggerFactory.getLogger(JmsConsumer::class.java)

    @JmsListener(destination = "trigger")
    fun processMessage(event: SystemMessage) {
        log.info("Received: ${event.message}")
        jmsProducer.send("jobOne", event)
    }

    @JmsListener(destination = "jobOne")
    fun processJobOne(event: SystemMessage) {
        val body = restTemplate.exchange(
            "https://web.test-shop-volkswagen-we.com/countries/mapping/list",
            HttpMethod.GET,
            HttpEntity<String>(HttpHeaders()),
            String::class.java
        ).body ?: ""
        log.info("Successfuly executed job one + key ${event.message}, data: $body")
        jmsProducer.send("jobTwo", event)
    }

    @JmsListener(destination = "jobTwo")
    fun processJobTwo(event: SystemMessage) {
        log.info("Received jobTwo ${event.message} + time: ${System.currentTimeMillis()}")
        try {
            val body = restTemplate.exchange(
                "https://web.test-shop-volkswagen-we.com/countries/mapping/list",
                HttpMethod.GET,
                HttpEntity<String>(HttpHeaders()),
                String::class.java
            ).body ?: ""
            log.info("Successfuly executed job Two + key ${event.message}, data: $body")
        } catch (
            e: Exception
        ) {
            log.info("Failed jobTwo ${event.message} + time: ${System.currentTimeMillis()}")
            throw e
        }
        jmsProducer.send("jobThree", event)
    }

    @JmsListener(destination = "jobThree")
    fun processJobThree(event: SystemMessage) {
        val body = restTemplate.exchange(
            "https://web.test-shop-volkswagen-we.com/countries/mapping/list",
            HttpMethod.GET,
            HttpEntity<String>(HttpHeaders()),
            String::class.java
        ).body ?: ""
        log.info("Successfuly executed job Three + key ${event.message}, data: $body")
    }
}