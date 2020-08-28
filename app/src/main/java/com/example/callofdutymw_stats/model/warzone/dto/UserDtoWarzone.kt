package com.example.callofdutymw_stats.model.warzone.dto

import com.example.callofdutymw_stats.model.warzone.all.UserAllWarzone
import com.google.gson.annotations.SerializedName

class UserDtoWarzone(
    @SerializedName("br_all")
    val userAllWarzone: UserAllWarzone
) {}