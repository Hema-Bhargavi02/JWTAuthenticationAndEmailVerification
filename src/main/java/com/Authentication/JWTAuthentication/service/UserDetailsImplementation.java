package com.Authentication.JWTAuthentication.service;

import com.Authentication.JWTAuthentication.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsImplementation implements UserDetailsService
{
    private final UserRepository repository;

    public UserDetailsImplementation(UserRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
    {
        return repository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
}
