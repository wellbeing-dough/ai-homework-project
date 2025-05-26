package com.ai.homework.aihomeworkproject.chat.repository

import com.ai.homework.aihomeworkproject.chat.domain.entity.Chat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@Repository
interface ChatRepository : JpaRepository<Chat, Long> {


    fun findFirstByUserIdOrderByCreatedAtDesc(userId: Long): Chat?


}
