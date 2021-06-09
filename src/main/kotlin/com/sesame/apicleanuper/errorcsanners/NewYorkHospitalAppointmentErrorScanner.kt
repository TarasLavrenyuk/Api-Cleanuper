package com.sesame.apicleanuper.errorcsanners

import com.sesame.apicleanuper.newyorkhospital.models.DoctorAppointment
import org.springframework.stereotype.Component
import java.util.Date

/**
 * We check here only values, that are important for us, the ones from the final response
 * List of errors to check is in Error enum
 */
@Component
class NewYorkHospitalAppointmentErrorScanner {

    fun scan(
        doctorAppointments: List<DoctorAppointment>
    ): MutableMap<ErrorDescription, MutableSet<String>> {
        val errorsData: MutableMap<ErrorDescription, MutableSet<String>> = mutableMapOf()
        val alreadyCheckedIds: MutableSet<String> = mutableSetOf()

        for (doctorAppointment in doctorAppointments) {
            if (alreadyCheckedIds.contains(doctorAppointment.id)) {
                errorsData[Error.APPOINTMENT_ID_DUPLICATION.name] = doctorAppointment.id
                continue
            } else {
                alreadyCheckedIds.add(doctorAppointment.id)
            }

            if (isValueMissing(doctorAppointment.doctor?.firstName)) {
                errorsData[Error.FIRST_NAME_MISSING.name] = doctorAppointment.id
            }

            if (isValueMissing(doctorAppointment.doctor?.lastName)) {
                errorsData[Error.LAST_NAME_MISSING.name] = doctorAppointment.id
            }

            if (isValueMissing(doctorAppointment.location?.name)) {
                errorsData[Error.LOCATION_NAME_MISSING.name] = doctorAppointment.id
            }

            if (isValueMissing(doctorAppointment.time)) {
                errorsData[Error.START_DATETIME_MISSING.name] = doctorAppointment.id
            }

            if (isValueMissing(doctorAppointment.durationInMinutes)) {
                errorsData[Error.DURATION_MISSING.name] = doctorAppointment.id
            }

            if (isValueMissing(doctorAppointment.service?.name)) {
                errorsData[Error.SERVICE_NAME_MISSING.name] = doctorAppointment.id
            }

            if (isValueMissing(doctorAppointment.service?.price)) {
                errorsData[Error.PRICE_MISSING.name] = doctorAppointment.id
            }

            if (isWrongDuration(doctorAppointment.durationInMinutes)) {
                errorsData[Error.DURATION_WRONG_VALUE.name] = doctorAppointment.id
            }

            if (isWrongPrice(doctorAppointment.service?.price)) {
                errorsData[Error.PRICE_WRONG_VALUE.name] = doctorAppointment.id
            }
        }

        return errorsData
    }

    private operator fun MutableMap<ErrorDescription, MutableSet<String>>.set(key: ErrorDescription, value: String) {
        if (this.contains(key)) {
            this[key]!!.add(value);
        } else {
            this[key] = mutableSetOf(value)
        }
    }

    private fun isValueMissing(value: String?): Boolean = value == null || value.isBlank()

    private fun isValueMissing(value: Long?): Boolean = value == null

    private fun isValueMissing(value: Date?): Boolean = value == null

    private fun isWrongDuration(duration: Long?) = duration != null && duration < 1

    private fun isWrongPrice(duration: Long?) = duration != null && duration < 0
}