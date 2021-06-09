package com.sesame.apicleanuper

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class DataConverterTest : BaseTest() {

    private val dataConverter: DataConverter = DataConverter()

    @Test
    fun `convert data success path`() {
        val initAppointment = generateDoctorAppointment(duration = 3)

        val output = dataConverter.fromNewYorkHospitalDoctorAppointmentsToDoctorsWithAppointments(listOf(initAppointment))

        Assertions.assertEquals(1, output.size)

        val doctorWithAppointments = output.first()
        Assertions.assertEquals(initAppointment.doctor!!.firstName, doctorWithAppointments.firstName)
        Assertions.assertEquals(initAppointment.doctor!!.lastName, doctorWithAppointments.lastName)
        Assertions.assertEquals(1, doctorWithAppointments.appointmentsByLocation.size)

        val appointmentsByLocation = doctorWithAppointments.appointmentsByLocation.first()
        Assertions.assertEquals(initAppointment.location!!.name, appointmentsByLocation.locationName)
        Assertions.assertEquals(1, appointmentsByLocation.appointments.size)

        val appointment = appointmentsByLocation.appointments.first()
        Assertions.assertEquals(initAppointment.id, appointment.appointmentId)
        Assertions.assertEquals("PT3M", appointment.duration)
        Assertions.assertEquals(initAppointment.time, appointment.startDateTime)
        Assertions.assertEquals(initAppointment.service!!.name, appointment.service.serviceName)
        Assertions.assertEquals(initAppointment.service!!.price, appointment.service.price)
    }

    @Test
    fun `data converting should prevent duplication`() {
        val appointmentId = UUID.randomUUID().toString()
        val firstName = UUID.randomUUID().toString()
        val lastName = UUID.randomUUID().toString()

        val input = listOf(
            generateDoctorAppointment(id = appointmentId, firstName = firstName, lastName = lastName),
            generateDoctorAppointment(id = appointmentId, firstName = firstName, lastName = lastName)
        )

        val output = dataConverter.fromNewYorkHospitalDoctorAppointmentsToDoctorsWithAppointments(input)

        Assertions.assertEquals(1, output.size)
        Assertions.assertEquals(1, output.first().appointmentsByLocation.size)
        Assertions.assertEquals(1, output.first().appointmentsByLocation.first().appointments.size)
        Assertions.assertEquals(
            appointmentId,
            output.first().appointmentsByLocation.first().appointments.first().appointmentId
        )
        Assertions.assertEquals(firstName, output.first().firstName)
        Assertions.assertEquals(lastName, output.first().lastName)
    }

    @Test
    fun `associate two appointments for the same doctor with different locations`() {
        val firstName = UUID.randomUUID().toString()
        val lastName = UUID.randomUUID().toString()

        val locationName1 = "city1"
        val locationName2 = "city2"
        val appointment1 =
            generateDoctorAppointment(firstName = firstName, lastName = lastName, locationName = locationName1)
        val appointment2 =
            generateDoctorAppointment(firstName = firstName, lastName = lastName, locationName = locationName2)
        val input = listOf(appointment1, appointment2)

        val output = dataConverter.fromNewYorkHospitalDoctorAppointmentsToDoctorsWithAppointments(input)

        Assertions.assertEquals(1, output.size)
        Assertions.assertEquals(2, output.first().appointmentsByLocation.size)
        Assertions.assertEquals(
            setOf(locationName1, locationName2),
            output.first().appointmentsByLocation.map { it.locationName }.toSet()
        )
    }

    @Test
    fun `associate two appointments for the same doctor with the same location`() {
        val firstName = UUID.randomUUID().toString()
        val lastName = UUID.randomUUID().toString()

        val appointment1 = generateDoctorAppointment(firstName = firstName, lastName = lastName)
        val appointment2 = generateDoctorAppointment(firstName = firstName, lastName = lastName)
        val input = listOf(appointment1, appointment2)

        val output = dataConverter.fromNewYorkHospitalDoctorAppointmentsToDoctorsWithAppointments(input)

        Assertions.assertEquals(1, output.size)
        Assertions.assertEquals(1, output.first().appointmentsByLocation.size)
        Assertions.assertEquals(2, output.first().appointmentsByLocation.first().appointments.size)
        Assertions.assertEquals(
            setOf(appointment1.id, appointment2.id),
            output.first().appointmentsByLocation.first().appointments.map { it.appointmentId }.toSet()
        )
    }
}