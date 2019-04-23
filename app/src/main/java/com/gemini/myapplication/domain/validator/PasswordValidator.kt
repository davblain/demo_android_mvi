package com.gemini.myapplication.domain.validator

import java.util.regex.Pattern

object PasswordValidator {
    fun isValid(value :CharSequence):Boolean {
        return Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}\$"
        ).matcher(value).matches()
    }
}