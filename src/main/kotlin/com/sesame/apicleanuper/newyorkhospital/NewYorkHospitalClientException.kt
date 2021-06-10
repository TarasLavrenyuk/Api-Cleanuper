package com.sesame.apicleanuper.newyorkhospital

class NewYorkHospitalClientException(
    override val message: String = "Cannot retrieve data via HospitalApi."
) : Exception(message)