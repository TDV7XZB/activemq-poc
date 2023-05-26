package com.ms.kafka.demo.config

import com.ms.kafka.demo.consumer.JmsConsumer
import org.apache.activemq.ActiveMQConnectionFactory
import org.apache.activemq.RedeliveryPolicy
import org.apache.activemq.command.ActiveMQQueue
import org.apache.activemq.command.ActiveMQTopic
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.EnableJms


/**
 * @author Ivelin Dimitrov
 */
@Configuration
@EnableJms
class JmsConfig {

    private val log = LoggerFactory.getLogger(JmsConsumer::class.java)

    @Value("\${spring.activemq.broker-url}")
    lateinit var brokerUrl: String

    @Value("\${spring.activemq.password}")
    lateinit var password: String

    @Value("\${spring.activemq.user}")
    lateinit var username: String

    @Value("\${spring.activemq.packages.trust-all}")
    lateinit var isTrustAllPackages: String


    @Bean
    fun connectionFactory(): ActiveMQConnectionFactory? {
        val connectionFactory = ActiveMQConnectionFactory()
        connectionFactory.brokerURL = brokerUrl
        connectionFactory.password = password
        connectionFactory.userName = username
        connectionFactory.isTrustAllPackages = isTrustAllPackages.toBoolean()

        //Config Redelivery Policy in Redelivery Policy Map
        val redeliveryPolicy = RedeliveryPolicy()
        redeliveryPolicy.initialRedeliveryDelay = 3000
        redeliveryPolicy.isUseCollisionAvoidance = true
        redeliveryPolicy.redeliveryDelay = 9000
        redeliveryPolicy.isUseExponentialBackOff = true
        redeliveryPolicy.maximumRedeliveryDelay = 72000
        redeliveryPolicy.backOffMultiplier = 1.5
        redeliveryPolicy.maximumRedeliveries = 21
        val redeliveryPolicyMap = connectionFactory.redeliveryPolicyMap
        redeliveryPolicyMap.put(ActiveMQQueue(">"), redeliveryPolicy)
        redeliveryPolicyMap.put(ActiveMQTopic(">"), redeliveryPolicy)
        connectionFactory.redeliveryPolicyMap = redeliveryPolicyMap
        connectionFactory.isNonBlockingRedelivery = true
        return connectionFactory
    }
//
//    private fun configureRedeliveryPolicy(connectionFactory: ActiveMQConnectionFactory): InitializingBean {
//        return InitializingBean {
//            val redeliveryPolicy = RedeliveryPolicy()
//            // configure redelivery policy
//            redeliveryPolicy.initialRedeliveryDelay = 10000
//            redeliveryPolicy.isUseCollisionAvoidance = true
//            redeliveryPolicy.redeliveryDelay = 10000
//            redeliveryPolicy.isUseExponentialBackOff = false
//            redeliveryPolicy.maximumRedeliveries = 3
//            connectionFactory.redeliveryPolicy = redeliveryPolicy
//        }
//    }
//
//    @Bean
//    @ConditionalOnBean(ActiveMQConnectionFactory::class)
//    fun connectionFactory(connectionFactory: ActiveMQConnectionFactory): InitializingBean {
//        return configureRedeliveryPolicy(connectionFactory)
//    }

//    @Bean
//    fun jmsListenerContainerFactory(
//        connectionFactory: ConnectionFactory
//    ): DefaultJmsListenerContainerFactory {
//        val factory = DefaultJmsListenerContainerFactory()
//        factory.setConnectionFactory(connectionFactory)
//        factory.setErrorHandler { t: Throwable? -> log.info("Error in listener!", t) }
//        factory.setConcurrency("5-10")
//        return factory
//    }

}