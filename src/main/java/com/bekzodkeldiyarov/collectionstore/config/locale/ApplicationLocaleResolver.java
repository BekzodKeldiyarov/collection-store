package com.bekzodkeldiyarov.collectionstore.config.locale;

import com.bekzodkeldiyarov.collectionstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Configuration
@Slf4j
public class ApplicationLocaleResolver extends SessionLocaleResolver {
    @Autowired
    UserService userService;

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String userName = securityContext.getAuthentication().getName();
        Locale userLocale = Locale.US;
        if (!userName.equals("anonymousUser")) {
            String localeOption = userService.getUsersPreferedLocaleOption(userName);
            if (localeOption != null) {
                userLocale = Locale.forLanguageTag(localeOption);
            }
        } else {
            SessionLocaleResolver slr = new SessionLocaleResolver();
            slr.setDefaultLocale(Locale.US);
        }
        return userLocale;
    }


    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        log.info("Locale changed to " + locale.getLanguage());
        super.setLocale(request, response, locale);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String userName = securityContext.getAuthentication().getName();
        if (!userName.equals("anonymousUser")) {
            userService.saveUsersPreferedLocaleOption(userName, locale);
        }
    }
}
