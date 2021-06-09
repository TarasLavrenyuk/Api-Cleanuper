package com.sesame.apicleanuper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AppointmentController {

    @Autowired
    private lateinit var appointmentService: AppointmentService

    @GetMapping("/appointments", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAppointments(): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok(appointmentService.loadNewYorkHospitalDoctorsAppointmentsWithErrorData())
    }
}