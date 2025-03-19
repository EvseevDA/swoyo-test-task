package com.github.evseevda.swoyotesttask.server.infrastructure.aspect;

import com.github.evseevda.swoyotesttask.server.exception.UserNotAuthenticatedException;
import com.github.evseevda.swoyotesttask.server.infrastructure.annotation.AuthenticationRequired;
import com.github.evseevda.swoyotesttask.server.security.context.SecurityContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CheckAuthenticationAspect {

    @Before("@annotation(authenticationRequired)")
    public void requireAuthentication(AuthenticationRequired authenticationRequired) {
        if (!SecurityContext.isAuthenticated()) {
            throw new UserNotAuthenticatedException("User is not authenticated.");
        }
    }

}
