package ch.simas.monitor

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class SimonApplication

fun main(args: Array<String>) {
    SpringApplication.run(SimonApplication::class.java, *args)
}
