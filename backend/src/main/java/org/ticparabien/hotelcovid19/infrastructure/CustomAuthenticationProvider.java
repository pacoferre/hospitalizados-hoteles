package org.ticparabien.hotelcovid19.infrastructure;

import org.ticparabien.hotelcovid19.domain.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationManager {

    private RoomService RoomService;

    @Autowired
    public CustomAuthenticationProvider(RoomService RoomService) {
        this.RoomService = RoomService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        if (RoomService.isOwnerAllowed(username, password)) {
            List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
            return new UsernamePasswordAuthenticationToken(username, password, authorities);
        }
        throw new BadCredentialsException("Invalid username or password");
    }
}
