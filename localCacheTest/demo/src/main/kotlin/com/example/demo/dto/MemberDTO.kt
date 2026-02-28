package com.example.demo.dto

import java.io.Serializable

data class MemberRequestDTO(
    val name: String,
    val age: Int
)

data class GetAllMembersRequestDTO(
    val isDbAccess: Boolean = false
) : Serializable

data class MemberGetRequestDTO(
    val id: Long
) : Serializable

data class MemberResponseDTO(
    val id: Long,
    val name: String,
    val age: Int
) : Serializable
