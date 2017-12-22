package edu.dmitrii.wschat.security;

import edu.dmitrii.wschat.domain.User;
import edu.dmitrii.wschat.event.ParticipantRepository;
import edu.dmitrii.wschat.services.AuthenticationService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;
import java.util.Optional;

@Log4j
public class SAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        Optional<User> user = Optional.ofNullable(authenticationService.getUser(token.getName(), String.valueOf(token.getCredentials())));
        List<GrantedAuthority> authorities;
        if (user.isPresent()) {
            log.info("User [" + token.getName() + "] pass = " + token.getCredentials());
            authorities = AuthorityUtils.createAuthorityList(user.get().getRoles().toArray(new String[0]));
            return new UsernamePasswordAuthenticationToken(token.getName(), token.getCredentials(), authorities);
        } else {
            log.info("No such user found! ");
            return null;
        }
    }
}
