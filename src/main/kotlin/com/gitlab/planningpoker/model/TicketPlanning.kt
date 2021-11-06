package com.gitlab.planningpoker.model

import javax.persistence.*

@Entity
@Table(name = "ticket_points")
class TicketPlanning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "ticket_number")
    var ticketNumber: String? = null


    @Column(name = "points")
    var points: String? = null

    @Column(name = "play_status")
    var playStatus: Boolean? = false

    @Column(name = "status")
    var status: Boolean? = false

    @Column(name = "user_id")
    var userId: Long? = null


    @Column(name = "user_name")
    var userName: String? = null

}