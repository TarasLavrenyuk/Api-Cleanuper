package com.sesame.apicleanuper

import com.sesame.apicleanuper.newyorkhospital.NewYorkHospitalClientException
import com.sesame.apicleanuper.newyorkhospital.NewYorkHospitalService
import com.sesame.apicleanuper.newyorkhospital.models.DoctorAppointment
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.util.*

@SpringBootTest
class NewYorkHospitalServiceTest: BaseTest() {

    private val restTemplate: RestTemplate = Mockito.mock(RestTemplate::class.java)

    private val getDoctorAppointmentsUrl: String = "http://some-url.com"

    private val service: NewYorkHospitalService = NewYorkHospitalService(
        restTemplate,
        getDoctorAppointmentsUrl
    )

    @BeforeEach
    fun setUp() {
        Mockito.reset(restTemplate)
    }

    @Test
    fun `request throws RestClientException test`() {
        Mockito
            .doThrow(RestClientException::class.java)
            .`when`(restTemplate).getForEntity(getDoctorAppointmentsUrl, Array<DoctorAppointment>::class.java)

        Assertions.assertThrows(NewYorkHospitalClientException::class.java) { service.getDoctorAppointments() }
    }

    @Test
    fun `request returns not 200 code`() {
        Mockito
            .doReturn(ResponseEntity<Array<DoctorAppointment>>(HttpStatus.FORBIDDEN))
            .`when`(restTemplate).getForEntity(getDoctorAppointmentsUrl, Array<DoctorAppointment>::class.java)

        Assertions.assertThrows(NewYorkHospitalClientException::class.java) { service.getDoctorAppointments() }
    }

    @Test
    fun `successful request without body returns empty list`() {
        Mockito
            .doReturn(ResponseEntity<Array<DoctorAppointment>>(HttpStatus.OK))
            .`when`(restTemplate).getForEntity(getDoctorAppointmentsUrl, Array<DoctorAppointment>::class.java)

        Assertions.assertEquals(
            emptyList<DoctorAppointment>(),
            service.getDoctorAppointments()
        )
    }

    @Test
    fun `expected path`() {
        val appointment1 = generateDoctorAppointment()
        val appointment2 = generateDoctorAppointment()

        Mockito
            .doReturn(ResponseEntity<Array<DoctorAppointment>>(arrayOf(appointment1, appointment2), HttpStatus.OK))
            .`when`(restTemplate).getForEntity(getDoctorAppointmentsUrl, Array<DoctorAppointment>::class.java)

        Assertions.assertEquals(
            listOf(appointment1, appointment2),
            service.getDoctorAppointments()
        )
    }
}