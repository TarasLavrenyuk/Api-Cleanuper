package com.sesame.apicleanuper.errorcsanners

import java.time.LocalDate

/**
 * This is basically error code with description
 */
typealias ErrorDescription = String

enum class Error {
    FIRST_NAME_MISSING,
    LAST_NAME_MISSING,
    LOCATION_NAME_MISSING,
    START_DATETIME_MISSING,
    DURATION_MISSING,
    SERVICE_NAME_MISSING,
    PRICE_MISSING,

    APPOINTMENT_ID_DUPLICATION,
    DURATION_WRONG_VALUE,
    PRICE_WRONG_VALUE;
}