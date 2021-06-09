package com.sesame.apicleanuper.newyorkhospital.models

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.Date

data class DoctorAppointment(
    val id: String,
    val doctor: Doctor?,
    val durationInMinutes: Long?,
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    val time: Date?,
    val service: Service?,
    val location: Location?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DoctorAppointment

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
