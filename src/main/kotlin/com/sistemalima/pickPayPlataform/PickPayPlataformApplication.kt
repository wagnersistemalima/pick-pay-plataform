package com.sistemalima.pickPayPlataform

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class PickPayPlataformApplication

fun main(args: Array<String>) {
	runApplication<PickPayPlataformApplication>(*args)
}
