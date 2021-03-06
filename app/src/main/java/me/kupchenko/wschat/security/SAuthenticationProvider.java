package me.kupchenko.wschat.security;

import me.kupchenko.wschat.domain.Role;
import me.kupchenko.wschat.domain.User;
import me.kupchenko.wschat.services.AuthenticationService;
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
import java.util.Set;

import static java.util.stream.Collectors.toSet;

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
            Set<String> collect = user.get().getRoles().stream().map(Role::getName).collect(toSet());
            authorities = AuthorityUtils.createAuthorityList(collect.toArray(new String[0]));
            return new UsernamePasswordAuthenticationToken(token.getName(), token.getCredentials(), authorities);
        } else {
            log.info("No such user found! ");
            return null;
        }
    }
}
