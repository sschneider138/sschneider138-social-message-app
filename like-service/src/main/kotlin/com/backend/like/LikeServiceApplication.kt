package com.backend.like

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LikeServiceApplication

fun main(args: Array<String>) {
	runApplication<LikeServiceApplication>(*args)
}