package com.praca.dyplomowa.backend.security


import com.praca.dyplomowa.backend.mongoDb.repository.UserRepository
import com.praca.dyplomowa.backend.security.auth.AuthFilter
import com.praca.dyplomowa.backend.security.auth.AuthManager
import com.praca.dyplomowa.backend.security.auth.ServerAuthConverter
import com.praca.dyplomowa.backend.security.jwtUtils.JWTService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.codec.json.AbstractJackson2Decoder
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.*
import reactor.adapter.rxjava.RxJava3Adapter.singleToMono
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    protected fun securityWebFilterChain(jwtService: JWTService,
                                         jwtAuthWebfilter: AuthenticationWebFilter,
                                         http: ServerHttpSecurity): SecurityWebFilterChain =
            http
                    .authorizeExchange()
                    .pathMatchers(HttpMethod.GET, "/").permitAll()
                    .pathMatchers(HttpMethod.POST, "/", "/auth/**").permitAll()
//                    .anyExchange().permitAll()/*.authenticated()*/
                    .anyExchange().authenticated()
                    .and()
                    .addFilterAt(jwtAuthWebfilter, SecurityWebFiltersOrder.AUTHORIZATION)
                    .addFilterAt(AuthFilter(jwtService), SecurityWebFiltersOrder.AUTHENTICATION)
                    .exceptionHandling()
                    .authenticationEntryPoint{ swe, e ->
                        Mono.fromRunnable { swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED) }
                    }
                    .accessDeniedHandler { swe, e ->
                        Mono.fromRunnable { swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)}
                    }
                    .and()
                    .httpBasic().disable()
                    .formLogin().disable()
                    .logout().disable()
                    .csrf().disable()
                    .build()

    @Bean
    fun jwtAuthWebFilter(authManager: AuthManager,
                         converter: ServerAuthConverter): AuthenticationWebFilter {
        val authenticationWebFilter = AuthenticationWebFilter(authManager)
        authenticationWebFilter.setServerAuthenticationConverter(converter)

        return authenticationWebFilter
    }

    @Bean
    fun userDetailsService(user: UserRepository): ReactiveUserDetailsService =
            ReactiveUserDetailsService { username: String -> singleToMono(user.findByUsername(username).map { UserDetailsExt(it)}) }
}

