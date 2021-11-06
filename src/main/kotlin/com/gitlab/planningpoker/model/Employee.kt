package com.gitlab.planningpoker.model

import javax.persistence.*

@Entity
@Table(name = "employees")
class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "first_name")
    var firstName: String? = null

    @Column(name = "last_name")
    var lastName: String? = null

    @Column(name = "email_id")
    var emailId: String? = null

    constructor() {}
    constructor(firstName: String?, lastName: String?, emailId: String?) : super() {
        this.firstName = firstName
        this.lastName = lastName
        this.emailId = emailId
    }
}