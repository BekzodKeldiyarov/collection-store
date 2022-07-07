package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.security.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserSessionImpl implements UserSession {

    private final SessionRegistry sessionRegistry;
    private final UserDetailsService userDetailsService;

    public UserSessionImpl(SessionRegistry sessionRegistry, UserDetailsService userDetailsService) {
        this.sessionRegistry = sessionRegistry;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void expireSessionForNonActiveUsers() {
        log.info(sessionRegistry.getAllPrincipals().toString());
        List<Object> principals = sessionRegistry.getAllPrincipals();
        log.info("principals size: " + principals.size());
        for (Object principal : principals) {
            if (principal instanceof MyUserDetails) {
                MyUserDetails myUserPrincipal = (MyUserDetails) userDetailsService.loadUserByUsername(((MyUserDetails) principal).getUsername());
                if (!myUserPrincipal.isEnabled()) {
                    List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(principal, false);
                    for (SessionInformation sessionInformation : sessionInformations) {
                        sessionInformation.expireNow();
                    }
                }
            }
        }
    }
}
