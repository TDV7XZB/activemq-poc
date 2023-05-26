package com.ms.kafka.demo

import java.io.Serializable


/**
 * @author Ivelin Dimitrov
 */
data class TriggerEvent (val id: Int) {

}


class SystemMessage(
    source: String = "",
    message: String = ""
) : Serializable {
    var source: String? = source
    var message: String? = message

    override fun toString(): String {
        return "SystemMessage{" +
                "source='" + source + '\'' +
                ", message='" + message + '\'' +
                '}'
    }

    companion object {
        private const val serialVersionUID = 1L
    }

}