package com.gitlab.planningpoker.model

import javax.persistence.*

@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "username")
    var username: String? = null

    @Column(name = "email")
    var email: String? = null

    @Column(name = "password")
    var password: String? = null

    @Column(name = "is_moderator")
    var isModerator: Boolean? = false

    @Column(name = "voted_point")
    var votedPoint: String? = null

    @Column(name = "show_vote")
    var showVote: Boolean? = null


    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableSet<Role> = mutableSetOf()

    @Column(name = "current_ticket")
    var currentTicket: String? = null
    fun User(
        firstname: String, lastname: String,
        username: String?, email: String?, password: String?
    ) {
         this.username = username
        this.email = email
        this.password = password
    }
}