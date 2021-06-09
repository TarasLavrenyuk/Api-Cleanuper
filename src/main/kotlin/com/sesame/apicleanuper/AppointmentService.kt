package com.sesame.apicleanuper

import com.sesame.apicleanuper.errorcsanners.NewYorkHospitalAppointmentErrorScanner
import com.sesame.apicleanuper.newyorkhospital.NewYorkHospitalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class AppointmentService {

    @Autowired
    private lateinit var newYorkHospitalService: NewYorkHospitalService

    @Autowired
    private lateinit var dataConverter: DataConverter

    @Autowired
    private lateinit var errorScanner: NewYorkHospitalAppointmentErrorScanner

    fun loadNewYorkHospitalDoctorsAppointmentsWithErrorData(): Map<String, Any> {
        val doctorAppointments = newYorkHospitalService.getDoctorAppointments()
        return mapOf(
            "doctorAppointments" to dataConverter.fromNewYorkHospitalDoctorAppointmentsToDoctorsWithAppointments(doctorAppointments),
            "errorData" to errorScanner.scan(doctorAppointments)
        )
    }
}

