package com.gitlab.planningpoker.message.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size


class LoginForm {
    var username: @NotBlank @Size(min = 3, max = 60) String? = null
    var password: @NotBlank @Size(min = 6, max = 40) String? = null
}