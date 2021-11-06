package com.gitlab.planningpoker.message.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size


class SignUpForm {
    var firstname: @NotBlank @Size(min = 3, max = 50) String? = null
    var lastname: @NotBlank @Size(min = 3, max = 50) String? = null
    var username: @NotBlank @Size(min = 3, max = 50) String? = null
    var email: @NotBlank @Size(max = 60) @Email String? = null
    var role: Set<String>? = null
    var password: @NotBlank @Size(min = 6, max = 40) String? = null
}