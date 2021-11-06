package com.gitlab.planningpoker.model

import com.gitlab.planningpoker.model.Role
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

/*
@Entity
@Table(
    name = "users",
    uniqueConstraints = [UniqueConstraint(columnNames = arrayOf("username")), UniqueConstraint(columnNames = arrayOf("email"))]
)


class User1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var username: @NotBlank @Size(max = 20) String? = null
    var email: @NotBlank @Size(max = 50) @Email String? = null
    var password: @NotBlank @Size(max = 120) String? = null

    var isModerator :Boolean?=false
    var votedPoint :String?=null
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableSet<Role> = mutableSetOf()
    constructor() {}
    constructor(username: String?, email: String?, password: String?) {
        this.username = username
        this.email = email
        this.password = password
    }
} */

