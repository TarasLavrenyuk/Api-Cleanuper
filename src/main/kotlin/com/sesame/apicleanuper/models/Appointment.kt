package com.sesame.apicleanuper.models

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.Date

data class Appointment(
    val appointmentId: String?,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:sss.Z")
    val startDateTime: Date?,
    val duration: String?,
    val service: Service,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Appointment

        if (appointmentId != other.appointmentId) return false

        return true
    }

    override fun hashCode(): Int {
        return appointmentId?.hashCode() ?: 0
    }
}
