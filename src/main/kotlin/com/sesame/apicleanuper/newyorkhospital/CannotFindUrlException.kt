package com.sesame.apicleanuper.newyorkhospital

class CannotFindUrlException(
    override val message: String = "Configuration is missing. Url was not found."
) : Exception(message)
