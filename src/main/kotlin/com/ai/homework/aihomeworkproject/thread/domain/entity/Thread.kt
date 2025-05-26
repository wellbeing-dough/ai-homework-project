package com.ai.homework.aihomeworkproject.thread.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "thread")
class Thread(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "chat_id", nullable = false)
    val chatId: Long,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,
)
