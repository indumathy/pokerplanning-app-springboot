package com.gitlab.planningpoker.controller

import com.gitlab.planningpoker.dto.JwtResponse11
import com.gitlab.planningpoker.dto.LoginRequest
import com.gitlab.planningpoker.dto.MessageResponse
import com.gitlab.planningpoker.dto.SignupRequest
import com.gitlab.planningpoker.model.ERole
import com.gitlab.planningpoker.model.Role
import com.gitlab.planningpoker.model.User
import com.gitlab.planningpoker.repository.RoleRepository
import com.gitlab.planningpoker.repository.UserRepository
import com.gitlab.planningpoker.security.jwt.JwtProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.function.Consumer
import java.util.stream.Collectors
import javax.validation.Valid



class AuthController {
    /*

      @CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")

@Autowired
      var authenticationManager: AuthenticationManager? = null

      @Autowired
      var userRepository: UserRepository? = null

      @Autowired
      var roleRepository: RoleRepository? = null

      @Autowired
      var encoder: PasswordEncoder? = null

      @Autowired
      var jwtUtils: JwtProvider? = null

      @PostMapping("/signin")
      fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<*> {
          val authentication = authenticationManager!!.authenticate(
              UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
          )
          SecurityContextHolder.getContext().authentication = authentication
          val jwt = jwtUtils!!.generateJwtToken(authentication)
          val userDetails = authentication.principal as UserDetails
          println(".1...22222...........login....authentication......"+userDetails.username)

          val roles = userDetails.authorities.stream()
              .map { item: GrantedAuthority? -> item!!.authority }
              .collect(Collectors.toList())
          return ResponseEntity.ok(
              JwtResponse11(
                  jwt,
                  userDetails.username,
                  roles
              )
          )
      }

      @PostMapping("/signup")
      fun registerUser(@Valid @RequestBody signUpRequest: SignupRequest): ResponseEntity<*> {
          if (userRepository!!.existsByUsername(signUpRequest.username?:"")) {
              return ResponseEntity
                  .badRequest()
                  .body(MessageResponse("Error: Username is already taken!"))
          }
          if (userRepository!!.existsByEmail(signUpRequest.email?:"") == true) {
              return ResponseEntity
                  .badRequest()
                  .body(MessageResponse("Error: Email is already in use!"))
          }

          // Create new user's account
          val user = User().also {
              it.username= signUpRequest.username
              it.email=  signUpRequest.email
              it.password=   encoder!!.encode(signUpRequest.password)
          }
          val strRoles = signUpRequest.role
          val roles: MutableSet<Role> = HashSet()
          if (strRoles == null) {
              val userRole = roleRepository!!.findByName(ERole.ROLE_USER)
                  ?.orElseThrow { RuntimeException("Error: Role is not found.") }
              if (userRole != null) {
                  roles.add(userRole)
              }
          } else {
              strRoles.forEach(Consumer { role: String? ->
                  when (role) {
                      "admin" -> {
                          val adminRole =
                              roleRepository!!.findByName(ERole.ROLE_ADMIN)
                                  ?.orElseThrow {
                                      RuntimeException(
                                          "Error: Role is not found."
                                      )
                                  }
                          if (adminRole != null) {
                              roles.add(adminRole)
                          }
                      }
                      "mod" -> {
                          val modRole =
                              roleRepository!!.findByName(ERole.ROLE_MODERATOR)
                                  ?.orElseThrow {
                                      RuntimeException(
                                          "Error: Role is not found."
                                      )
                                  }
                          if (modRole != null) {
                              roles.add(modRole)
                          }
                      }
                      else -> {
                          val userRole = roleRepository!!.findByName(ERole.ROLE_USER)
                              ?.orElseThrow {
                                  RuntimeException(
                                      "Error: Role is not found."
                                  )
                              }
                          if (userRole != null) {
                              roles.add(userRole)
                          }
                      }
                  }
              })
          }
          user.roles = roles
          userRepository!!.save(user)
          return ResponseEntity.ok(MessageResponse("User registered successfully!"))
      }

   */
}