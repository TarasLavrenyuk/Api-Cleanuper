package com.sesame.apicleanuper

import com.sesame.apicleanuper.models.AppointmentsWithLocation
import com.sesame.apicleanuper.models.DoctorWithAppointments
import com.sesame.apicleanuper.models.Appointment
import com.sesame.apicleanuper.models.Service
import com.sesame.apicleanuper.newyorkhospital.models.DoctorAppointment
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class DataConverter {

    fun fromNewYorkHospitalDoctorAppointmentsToDoctorsWithAppointments(newYorkHospitalAppointments: List<DoctorAppointment>): List<DoctorWithAppointments> {
        val doctorsWithAppointments = mutableMapOf<DoctorInfo, DoctorWithAppointments>()
        val doctorsAppointmentsLocations = mutableMapOf<DoctorInfoWithLocation, MutableSet<Appointment>>()
        for (newYorkHospitalAppointment in newYorkHospitalAppointments) {

            val doctorInfo = DoctorInfo(
                firstName = newYorkHospitalAppointment.doctor?.firstName,
                lastName = newYorkHospitalAppointment.doctor?.lastName
            )
            val locationName = newYorkHospitalAppointment.location?.name
            val appointment = fromNewYorkHospitalDoctorAppointmentToAppointment(newYorkHospitalAppointment)

            if (!doctorsWithAppointments.contains(doctorInfo)) {
                val appointments = mutableSetOf(appointment)
                DoctorWithAppointments(
                    firstName = newYorkHospitalAppointment.doctor?.firstName,
                    lastName = newYorkHospitalAppointment.doctor?.lastName
                ).apply {
                    this.appointmentsByLocation.add(
                        AppointmentsWithLocation(
                            locationName = locationName,
                            appointments = appointments
                        )
                    )
                }.also {
                    doctorsWithAppointments[doctorInfo] = it
                    doctorsAppointmentsLocations[DoctorInfoWithLocation(doctorInfo, locationName)] = appointments
                }
            } else {
                val doctorWithAppointments = doctorsWithAppointments[doctorInfo]!!

                if (!doctorsAppointmentsLocations.contains(
                        DoctorInfoWithLocation(
                            doctorInfo,
                            locationName
                        )
                    )) {
                    val appointments = mutableSetOf(appointment)
                    AppointmentsWithLocation(
                        locationName = locationName,
                        appointments = appointments
                    ).also {
                        doctorWithAppointments.appointmentsByLocation.add(it)
                        doctorsAppointmentsLocations[DoctorInfoWithLocation(
                            doctorInfo,
                            locationName
                        )] = appointments
                    }
                } else {
                    doctorsAppointmentsLocations[DoctorInfoWithLocation(doctorInfo, locationName)]!!.add(appointment)
                }
            }
        }

        return doctorsWithAppointments.values.toList()
    }

    private fun fromNewYorkHospitalDoctorAppointmentToAppointment(newYorHospitalAppointment: DoctorAppointment): Appointment {
        return Appointment(
            appointmentId = newYorHospitalAppointment.id,
            startDateTime = newYorHospitalAppointment.time,
            duration = newYorHospitalAppointment.durationInMinutes.toISO8601(),
            service = Service(
                serviceName = newYorHospitalAppointment.service?.name,
                price = newYorHospitalAppointment.service?.price
            )
        )
    }

    private fun Long?.toISO8601() = this?.let {
        Duration.ofMinutes(it).toString()
    }

    private data class DoctorInfo(
        val firstName: String?,
        val lastName: String?
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as DoctorInfo

            if (firstName != other.firstName) return false
            if (lastName != other.lastName) return false

            return true
        }

        override fun hashCode(): Int {
            var result = firstName?.hashCode() ?: 0
            result = 31 * result + (lastName?.hashCode() ?: 0)
            return result
        }
    }

    private data class DoctorInfoWithLocation(
        val firstName: String?,
        val lastName: String?,
        val location: String?
    ) {
        constructor(
            doctorInfo: DoctorInfo,
            location: String?
        ) : this(
            firstName = doctorInfo.firstName,
            lastName = doctorInfo.lastName,
            location = location
        )

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as DoctorInfoWithLocation

            if (firstName != other.firstName) return false
            if (lastName != other.lastName) return false
            if (location != other.location) return false

            return true
        }

        override fun hashCode(): Int {
            var result = firstName?.hashCode() ?: 0
            result = 31 * result + (lastName?.hashCode() ?: 0)
            result = 31 * result + (location?.hashCode() ?: 0)
            return result
        }
    }
}