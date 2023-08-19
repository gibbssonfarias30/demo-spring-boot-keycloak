package com.backfcdev.demospringbootkeycloak.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;

public class GrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        return Optional.ofNullable(source.getClaimAsMap("realm_access"))
                .map(realmAccess -> (List<String>) realmAccess.get("roles"))
                .orElse(List.of())
                .stream()
                .map(rn -> new SimpleGrantedAuthority("ROLE_" + rn))
                .collect(Collectors.toList());
    }
}