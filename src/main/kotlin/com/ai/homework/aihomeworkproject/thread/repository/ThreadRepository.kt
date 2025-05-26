package com.ai.homework.aihomeworkproject.thread.repository

import com.ai.homework.aihomeworkproject.thread.domain.entity.Thread
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ThreadRepository : JpaRepository<Thread, Long> {

    @Query("SELECT t FROM Thread t WHERE t.userId = :userId ORDER BY t.createdAt DESC")
    fun findLatestByUserId(userId: Long): Thread?

    fun findByChatId(chatId: Long): Thread?

    fun findAllByUserId(userId: Long, pageable: Pageable): Page<Thread>

}
