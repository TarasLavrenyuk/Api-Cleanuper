package com.sesame.apicleanuper.models

data class DoctorWithAppointments(
    val firstName: String?,
    val lastName: String?,
    val appointmentsByLocation: MutableSet<AppointmentsWithLocation> = mutableSetOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DoctorWithAppointments

        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        return result
    }
}