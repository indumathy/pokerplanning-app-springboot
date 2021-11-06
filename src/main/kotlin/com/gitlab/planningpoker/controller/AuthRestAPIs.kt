package com.gitlab.planningpoker.controller

import com.gitlab.planningpoker.message.request.LoginForm
import com.gitlab.planningpoker.message.request.SignUpForm
import com.gitlab.planningpoker.message.response.JwtResponse
import com.gitlab.planningpoker.message.response.ResponseMessage
import com.gitlab.planningpoker.model.Role
import com.gitlab.planningpoker.model.RoleName
import com.gitlab.planningpoker.model.User
import com.gitlab.planningpoker.repository.RoleRepository
import com.gitlab.planningpoker.repository.UserRepository
import com.gitlab.planningpoker.security.jwt.JwtProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.function.Consumer
import javax.validation.Valid


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthRestAPIs {
    @Autowired
    var authenticationManager: AuthenticationManager? = null

    @Autowired
    var userRepository: UserRepository? = null

    @Autowired
    var roleRepository: RoleRepository? = null

    @Autowired
    var encoder: PasswordEncoder? = null

    @Autowired
    var jwtProvider: JwtProvider? = null
    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: @Valid LoginForm?): ResponseEntity<*> {
         val authentication = authenticationManager!!.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest?.username ?: "", loginRequest?.password ?: "")
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtProvider?.generateJwtToken(authentication)?:""
        val userDetails = authentication.principal as UserDetails
        return ResponseEntity.ok<Any>(JwtResponse(jwt, userDetails.username, userDetails.authorities ))
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody signUpRequest: @Valid SignUpForm?): ResponseEntity<*> {
        if(roleRepository == null) return ResponseEntity.ok("")
        if (userRepository?.existsByUsername(signUpRequest?.username?:"") == true) {
            return ResponseEntity<Any>(
                ResponseMessage("Fail -> Username is already taken!"),
                HttpStatus.BAD_REQUEST
            )
        }
        if (userRepository?.existsByEmail(signUpRequest?.email?:"") == true) {
            return ResponseEntity<Any>(
                ResponseMessage("Fail -> Email is already in use!"),
                HttpStatus.BAD_REQUEST
            )
        }

        // Creating user's account
        val user = User().also {
            it.username=  signUpRequest?.username?:""
            it.email=  signUpRequest?.email?:""
            it.password=  encoder!!.encode(signUpRequest?.password)
        }
        val roles: MutableSet<Role> = HashSet<Role>()
        if (signUpRequest?.role != null) {
            val strRoles: Set<String> = signUpRequest.role!!
            strRoles.forEach(Consumer { role: String? ->
                when (role) {
                    "admin" -> {
                        val adminRole: Role = roleRepository!!.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow { RuntimeException("Fail! -> Cause: User Role not find.") }
                        roles.add(adminRole)
                    }
                    "pm" -> {
                        val pmRole: Role = roleRepository!!.findByName(RoleName.ROLE_PM)
                            .orElseThrow { RuntimeException("Fail! -> Cause: User Role not find.") }
                        roles.add(pmRole)
                    }
                    else -> {
                        val userRole: Role = roleRepository!!.findByName(RoleName.ROLE_USER)
                            .orElseThrow { RuntimeException("Fail! -> Cause: User Role not find.") }
                        roles.add(userRole)
                    }
                }
            })
        } else {
            // default mode : User register
            val userRole: Role = roleRepository!!.findByName(RoleName.ROLE_USER)
                .orElseThrow { RuntimeException("Fail! -> Cause: User Role not find.") }
            roles.add(userRole)
        }
        user.roles = roles
        userRepository?.save(user)
        return ResponseEntity<Any>(
            ResponseMessage(
                "User " + signUpRequest?.firstname.toString() + " is registered successfully!"
            ), HttpStatus.OK
        )
    }
}