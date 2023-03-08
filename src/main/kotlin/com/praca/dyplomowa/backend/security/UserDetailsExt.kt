package com.praca.dyplomowa.backend.security

import com.praca.dyplomowa.backend.mongoDb.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

class UserDetailsExt(
        val user: User
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        user.roles.stream()
                .map{ role -> SimpleGrantedAuthority(role)}
                .collect(Collectors.toList())

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}