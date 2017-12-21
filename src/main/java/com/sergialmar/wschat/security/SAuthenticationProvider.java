package com.sergialmar.wschat.security;

import com.sergialmar.wschat.event.ParticipantRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;

@Log4j
public class SAuthenticationProvider implements AuthenticationProvider {

    private static final String SECURE_ADMIN_PASSWORD = "rockandroll";

    @Autowired
    private ParticipantRepository participantRepository;

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        List<GrantedAuthority> authorities = SECURE_ADMIN_PASSWORD.equals(token.getCredentials()) ?
                AuthorityUtils.createAuthorityList("ROLE_ADMIN") : null;
        log.info("User [" + token.getName() + "] pass = " + token.getCredentials());
        boolean logged = participantRepository.isLogged(token.getName());
        log.info("isLogged " + logged);
        if (logged) {
            return null;
        }
        return new UsernamePasswordAuthenticationToken(token.getName(), token.getCredentials(), authorities);
    }
}
