package com.example.moodle.db.models

data class LoginRequest(
    var email : String ? = null,
    var password : String ? = null
)