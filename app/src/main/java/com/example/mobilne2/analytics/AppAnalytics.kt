package com.example.mobilne2.analytics

class AppAnalytics constructor(

) {

    private val log = mutableListOf<String>()

    fun log(message: String) {
        log.add(message)
    }

}