package com.praca.dyplomowa.backend.logger

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class ApplicationLogger: IApplicationLogger {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun getLogger() = logger

    override fun info(message: String){
        getLogger().info(message)
    }

    override fun warn(message: String){
        getLogger().warn(message)
    }

    override fun error(message: String){
        getLogger().error(message)
    }

    override fun trace(message: String){
        getLogger().trace(message)
    }

    override fun debug(message: String) {
        getLogger().debug(message)
    }
}