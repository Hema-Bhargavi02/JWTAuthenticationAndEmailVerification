package com.Authentication.JWTAuthentication.restcontroller;

import com.Authentication.JWTAuthentication.entity.User;
import com.Authentication.JWTAuthentication.service.AuthenticationService;
import jakarta.mail.MessagingException;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller
{
    private final AuthenticationService authenticationService;

    public Controller(AuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signin")
    public String signIn(@RequestBody User request) throws MessagingException
    {
       authenticationService.signIn(request);
       return "SignedIn successfully";
    }

    @PostMapping("/verify")
    public String verification(@RequestParam("token") String token) throws MessagingException
    {
       return authenticationService.verifyEmail(token);
    }

    @PostMapping("/login")
    public String login(@RequestBody User request)
    {
        return authenticationService.authenticate(request);
    }
}
