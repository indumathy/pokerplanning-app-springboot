package com.gitlab.planningpoker.repository

import com.gitlab.planningpoker.model.TicketPlanning
import com.gitlab.planningpoker.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository


interface TicketPlanningRepository : JpaRepository<TicketPlanning?, Long?> {

}