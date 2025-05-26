package com.ai.homework.aihomeworkproject.chat.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "chat")
class Chat(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    val userId: Long,

    var question: String? = null,

    var answer: String? = null,

    @Column(name = "created_at")
    var createdAt: LocalDateTime? = null
) {
}