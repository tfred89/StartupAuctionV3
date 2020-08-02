package com.stanfan.StartupAuctionV3.security;


import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.stanfan.StartupAuctionV3.model.Owner;
import com.stanfan.StartupAuctionV3.model.OwnerDAO;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserModelDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserModelDetailsService.class);

    private final OwnerDAO ownerDAO;

    public UserModelDetailsService(OwnerDAO ownerDAO) {
        this.ownerDAO = ownerDAO;
    }

    @Override
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating user '{}'", login);
        String lowercaseLogin = login.toLowerCase();
        return createSpringSecurityUser(lowercaseLogin,ownerDAO.findByUsername(lowercaseLogin));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, Owner owner) {
        if (!owner.isActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = owner.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(owner.getOwnerName(),
        		owner.getPassword(),
                grantedAuthorities);
    }
}

