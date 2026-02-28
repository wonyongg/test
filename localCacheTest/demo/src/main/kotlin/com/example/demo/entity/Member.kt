package com.example.demo.entity

import jakarta.persistence.*

@Entity
@Table(name = "member")
data class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val age: Int
) {
    // name, age만 받는 보조 생성자
    constructor(name: String, age: Int) : this(
        id = null,
        name = name,
        age = age
    )
}
