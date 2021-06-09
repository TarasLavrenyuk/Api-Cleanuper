package com.sesame.apicleanuper

import com.sesame.apicleanuper.errorcsanners.Error
import com.sesame.apicleanuper.errorcsanners.NewYorkHospitalAppointmentErrorScanner
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID

@SpringBootTest
class NewYorkHospitalAppointmentErrorScannerTest : BaseTest() {

    private val errorScanner: NewYorkHospitalAppointmentErrorScanner = NewYorkHospitalAppointmentErrorScanner()

    @Test
    fun `appointment id duplication error test`() {
        val randomId = UUID.randomUUID().toString()

        val errorsData = errorScanner.scan(
            mutableListOf(
                generateDoctorAppointment(id = randomId),
                generateDoctorAppointment(id = randomId)
            )
        )

        Assertions.assertEquals(
            errorsData[Error.APPOINTMENT_ID_DUPLICATION.name],
            mutableSetOf(randomId)
        )
    }

    @Test
    fun `first name missing error test`() {
        val doctorAppointment1 = generateDoctorAppointment(firstName = null)
        val doctorAppointment2 = generateDoctorAppointment(firstName = "   ")

        val errorsData = errorScanner.scan(
            mutableListOf(doctorAppointment1, doctorAppointment2)
        )

        Assertions.assertEquals(
            errorsData[Error.FIRST_NAME_MISSING.name],
            mutableSetOf(doctorAppointment1.id, doctorAppointment2.id)
        )
    }

    @Test
    fun `last name missing error test`() {
        val doctorAppointment1 = generateDoctorAppointment(lastName = null)
        val doctorAppointment2 = generateDoctorAppointment(lastName = "   ")
        val errorsData = errorScanner.scan(
            mutableListOf(doctorAppointment1, doctorAppointment2)
        )

        Assertions.assertEquals(
            errorsData[Error.LAST_NAME_MISSING.name],
            mutableSetOf(doctorAppointment1.id, doctorAppointment2.id)
        )
    }

    @Test
    fun `location name missing error test`() {
        val doctorAppointment = generateDoctorAppointment(locationName = null)
        val errorsData = errorScanner.scan(
            mutableListOf(doctorAppointment)
        )

        Assertions.assertEquals(
            errorsData[Error.LOCATION_NAME_MISSING.name],
            mutableSetOf(doctorAppointment.id)
        )
    }

    @Test
    fun `start datetime missing error test`() {
        val doctorAppointment = generateDoctorAppointment(time = null)
        val errorsData = errorScanner.scan(
            mutableListOf(doctorAppointment)
        )

        Assertions.assertEquals(
            errorsData[Error.START_DATETIME_MISSING.name],
            mutableSetOf(doctorAppointment.id)
        )
    }

    @Test
    fun `duration missing error test`() {
        val doctorAppointment = generateDoctorAppointment(duration = null)
        val errorsData = errorScanner.scan(
            mutableListOf(doctorAppointment)
        )

        Assertions.assertEquals(
            errorsData[Error.DURATION_MISSING.name],
            mutableSetOf(doctorAppointment.id)
        )
    }

    @Test
    fun `duration wrong value error test`() {
        val doctorAppointment = generateDoctorAppointment(duration = -5)
        val errorsData = errorScanner.scan(
            mutableListOf(doctorAppointment)
        )

        Assertions.assertEquals(
            errorsData[Error.DURATION_WRONG_VALUE.name],
            mutableSetOf(doctorAppointment.id)
        )
    }

    @Test
    fun `service name missing error test`() {
        val doctorAppointment = generateDoctorAppointment(serviceName = null)
        val errorsData = errorScanner.scan(
            mutableListOf(doctorAppointment)
        )

        Assertions.assertEquals(
            errorsData[Error.SERVICE_NAME_MISSING.name],
            mutableSetOf(doctorAppointment.id)
        )
    }

    @Test
    fun `price missing error test`() {
        val doctorAppointment = generateDoctorAppointment(price = null)
        val errorsData = errorScanner.scan(
            mutableListOf(doctorAppointment)
        )

        Assertions.assertEquals(
            errorsData[Error.PRICE_MISSING.name],
            mutableSetOf(doctorAppointment.id)
        )
    }

    @Test
    fun `price wrong value error test`() {
        val doctorAppointment = generateDoctorAppointment(price = -5)
        val errorsData = errorScanner.scan(
            mutableListOf(doctorAppointment)
        )

        Assertions.assertEquals(
            errorsData[Error.PRICE_WRONG_VALUE.name],
            mutableSetOf(doctorAppointment.id)
        )
    }
}