package com.praca.dyplomowa.backend.logger

import org.slf4j.Logger

interface IApplicationLogger {

    fun getLogger(): Logger
    fun info(message: String)
    fun warn(message: String)
    fun error(message: String)
    fun trace(message: String)
    fun debug(message: String)

}