package com.bekzodkeldiyarov.collectionstore.oauth2;

import com.bekzodkeldiyarov.collectionstore.model.User;
import com.bekzodkeldiyarov.collectionstore.security.MyUserDetails;
import com.bekzodkeldiyarov.collectionstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Component
@Slf4j
public class OAuth2LoginSuccessHandler implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {
    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        log.info("------------------------ logged in ----------------");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            MyOAuth2User user = (MyOAuth2User) principal;
            if (userService.findByUsername(user.getName()) != null) ;
            {
                log.info(user.getName());
                User userToSave = new User();
                userToSave.setUsername(user.getName());
                userToSave.setEmail(user.getEmail());
                userToSave.setEnabled(true);
                userService.save(userToSave);
            }
        } catch (ClassCastException e) {
            try {
                MyUserDetails user = (MyUserDetails) principal;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
