package com.praca.dyplomowa.backend.security


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    protected fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
            http
                    .authorizeExchange()
                    .pathMatchers(HttpMethod.GET, "/").permitAll()
                    .pathMatchers(HttpMethod.POST, "/"/*, "/me"*/).permitAll()
                    .anyExchange().permitAll()/*.authenticated()*/
                    .and()
                    .httpBasic().disable()
                    .formLogin().disable()
                    .logout().disable()
                    .csrf().disable()
                    .build()

}

