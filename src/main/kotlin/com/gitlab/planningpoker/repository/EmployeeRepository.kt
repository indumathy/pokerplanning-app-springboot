package com.gitlab.planningpoker.repository

import com.gitlab.planningpoker.model.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee?, Long?>
/*
@Repository


interface TicketPlanningRepository : JpaRepository<TicketPlanning?, Long?> {

} */