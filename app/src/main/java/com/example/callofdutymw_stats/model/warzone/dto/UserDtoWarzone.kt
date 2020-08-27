package com.example.callofdutymw_stats.model.warzone.dto

import com.example.callofdutymw_stats.model.warzone.UserAll
import com.google.gson.annotations.SerializedName

class UserDtoWarzone(
    @SerializedName("br")
    val userWarzoneAll: UserAll
) {}