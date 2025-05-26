package com.ai.homework.aihomeworkproject.common

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["com.ai.homework.aihomeworkproject"])
class FeignConfig