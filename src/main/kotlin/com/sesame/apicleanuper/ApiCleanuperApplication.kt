package com.sesame.apicleanuper

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
@Configuration
class ApiCleanuperApplication : WebMvcConfigurer {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.build()
    }
}

fun main(args: Array<String>) {
    runApplication<ApiCleanuperApplication>(*args)
}
