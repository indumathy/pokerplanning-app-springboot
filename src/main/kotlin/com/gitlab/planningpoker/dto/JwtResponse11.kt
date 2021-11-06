package com.gitlab.planningpoker.dto

class JwtResponse11(
    var accessToken: String,
  //  var id: Long,
    var username: String,
  //  var email: String,
    val roles: List<String>
) {
    var tokenType = "Bearer"

}