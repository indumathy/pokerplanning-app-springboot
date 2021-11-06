package com.gitlab.planningpoker.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


class TestController {
 /*
   @CrossOrigin(origins = ["http://localhost:3000"])
@RestController
@RequestMapping("/api/test")
@GetMapping("/all")
    fun allAccess(): String {
//        val peint=  SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
  //      println(".......................username....."+peint.username+".....")

        return "Public Content."
    }

    @GetMapping("/user")
     fun userAccess(): String {
        val peint=  SecurityContextHolder.getContext().authentication.principal as UserDetails
        println("................ssss.......username....."+peint.username+".....")

        return "User Content."
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    fun moderatorAccess(): String {   val peint=  SecurityContextHolder.getContext().authentication.principal as UserDetails
        println(".........bb..............username....."+peint.username+".....")

        return "Moderator Board."
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    fun adminAccess(): String {
        val peint=  SecurityContextHolder.getContext().authentication.principal as UserDetails
        println("........aa...............username....."+peint.username+".....")

        return "Admin Board."
    }


  */
}