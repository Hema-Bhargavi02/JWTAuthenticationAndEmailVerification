package com.Authentication.JWTAuthentication.service;

import com.Authentication.JWTAuthentication.email.EmailContext;
import com.Authentication.JWTAuthentication.entity.User;
import com.Authentication.JWTAuthentication.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService
{
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;


    public AuthenticationService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, EmailService emailService)
    {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }


    public void signIn(User request) throws MessagingException {
        User user = new User();
        user.setUserName(request.getUserName());
        user.setPass(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setVerified(false);
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        String verificationLink = "http://localhost:8080/verify?token=" + token;
        EmailContext emailContext = new EmailContext();
        emailContext.setTo(request.getEmail());
        emailContext.setSubject("Email Verification");
        emailContext.setFrom("hemabhargavikaranam@gmail.com");
        emailContext.addContextVariable("verificationLink", verificationLink);
        emailService.sendMail(emailContext);
    }

    public String verifyEmail(String token)
    {
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (user.isVerified())
        {
            return "Email already verified!";
        }

        user.setVerified(true);
        userRepository.save(user);

        return "Email successfully verified!";
    }


    public String authenticate(User request)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        if(!user.isVerified())
        {
            return "check your mail for email verification";
        }
        logger.info(user.getPass());
        return jwtService.generateToken(user);
    }

}
