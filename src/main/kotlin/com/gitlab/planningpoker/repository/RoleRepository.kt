package com.gitlab.planningpoker.repository

import com.gitlab.planningpoker.model.ERole
import com.gitlab.planningpoker.model.Role
import com.gitlab.planningpoker.model.RoleName
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(name: RoleName): Optional<Role>
}