package com.sesame.apicleanuper.models

data class AppointmentsWithLocation(
    val locationName: String?,
    val appointments: MutableSet<Appointment> = mutableSetOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AppointmentsWithLocation

        if (locationName != other.locationName) return false

        return true
    }

    override fun hashCode(): Int {
        return locationName.hashCode()
    }
}
