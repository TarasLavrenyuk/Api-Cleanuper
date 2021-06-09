package com.sesame.apicleanuper

import com.sesame.apicleanuper.newyorkhospital.models.Doctor
import com.sesame.apicleanuper.newyorkhospital.models.DoctorAppointment
import com.sesame.apicleanuper.newyorkhospital.models.Location
import com.sesame.apicleanuper.newyorkhospital.models.Service
import java.util.*

open class BaseTest {

    protected fun generateDoctorAppointment(
        id: String = UUID.randomUUID().toString(),
        duration: Long? = 5,
        time: Date? = Date(),
        firstName: String? = "firstName_${UUID.randomUUID()}",
        lastName: String? = "lastName_${UUID.randomUUID()}",
        serviceName: String? = "service ${UUID.randomUUID()}",
        price: Long? = 10,
        locationName: String? = "New York",
        timeZone: String? = "EDT"
    ): DoctorAppointment = DoctorAppointment(
        id = id,
        durationInMinutes = duration,
        time = time,
        doctor = Doctor(firstName = firstName, lastName = lastName),
        service = Service(name = serviceName, price = price),
        location = Location(name = locationName, timeZone = timeZone)
    )


}
