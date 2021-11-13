package com.gitlab.planningpoker.dto

class UserDto
    (
    var id: Long? = null,
    var userName: String? = null,
    var isModerator: Boolean? = false,
    var voted: Boolean? = false,
    var showVotedPoint: Boolean? = false,
    var votedPoint: String? = null
)