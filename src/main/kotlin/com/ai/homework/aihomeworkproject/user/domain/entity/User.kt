package com.ai.homework.aihomeworkproject.user.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,

    @Column(unique = true)
    val email: String,

    val password: String,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,

    @Enumerated(EnumType.STRING)
    val role: Role = Role.MEMBER
)

enum class Role {
    MEMBER, ADMIN
}
