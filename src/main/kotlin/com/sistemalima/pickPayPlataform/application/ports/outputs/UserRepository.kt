package com.sistemalima.pickPayPlataform.application.ports.outputs

import com.sistemalima.pickPayPlataform.adapters.repository.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<UserEntity, Long> {

    fun existsByEmail(email: String): Boolean

    fun existsByDocument(document: String): Boolean
}