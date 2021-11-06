package com.gitlab.planningpoker.security


import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration


@Component
@ConfigurationProperties("cors")
class CorsConfig {
    var enabled: Boolean = false
    var allowCredentials: Boolean = false
    var allowedOrigins = mutableListOf<String>()
    var allowedMethods = mutableListOf<String>()
    var allowedHeaders = mutableListOf<String>()
    var exposedHeaders = mutableListOf<String>()

    fun build(): CorsConfiguration {
        val cors = CorsConfiguration()
        if (allowedOrigins.isNotEmpty()) {
            cors.allowedOrigins = allowedOrigins
        }
        if (allowedMethods.isNotEmpty()) {
            cors.allowedMethods = allowedMethods
        }
        if (allowedHeaders.isNotEmpty()) {
            cors.allowedHeaders = allowedHeaders
        }
        if (exposedHeaders.isNotEmpty()) {
            cors.exposedHeaders = exposedHeaders
        }
        cors.allowCredentials = allowCredentials

        return cors
    }

}