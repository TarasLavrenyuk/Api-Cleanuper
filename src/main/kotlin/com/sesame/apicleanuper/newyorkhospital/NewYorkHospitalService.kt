package com.sesame.apicleanuper.newyorkhospital

import com.sesame.apicleanuper.newyorkhospital.models.DoctorAppointment
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@Service
class NewYorkHospitalService constructor(
    @Autowired
    private val restTemplate: RestTemplate,

    @Value("\${apiCleanuper.url.newYorkHospital.getAppointments}")
    private val getDoctorAppointmentsUrl: String
) {

    private val logger = LoggerFactory.getLogger(NewYorkHospitalService::class.java)

    @Throws(NewYorkHospitalClientException::class)
    fun getDoctorAppointments(): List<DoctorAppointment> {
        val response: ResponseEntity<Array<DoctorAppointment>>
        try {
            response = restTemplate.getForEntity(getDoctorAppointmentsUrl, Array<DoctorAppointment>::class.java)
        } catch (e: RestClientException) {
            logger.error(e.stackTraceToString())
            throw NewYorkHospitalClientException()
        }

        if (response.statusCode == HttpStatus.OK) {
            return response.body?.asList() ?: emptyList()
        } else {
            logger.warn(
                "Something wrong with the request.\nResponse: $response.\nResponse body: ${response.body}"
            )
            throw NewYorkHospitalClientException()
        }
    }
}