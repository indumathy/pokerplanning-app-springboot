package com.gitlab.planningpoker

import com.gitlab.planningpoker.PlanningPokerApplication
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
@SpringBootApplication
class PlanningPokerApplication {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(this::class.java.declaringClass)!!
    }
}

fun main(args: Array<String>) {

    runApplication<PlanningPokerApplication>(*args)
}
