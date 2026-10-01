package com.management.Employee_Management.security;

import com.management.Employee_Management.model.Users;
import com.management.Employee_Management.repository.UsersRepository;
import com.management.Employee_Management.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerDetailsSecurity implements UserDetailsService {

    private  final UsersRepository usersRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Users byUsername = usersRepository.findByUsername(username);
            if(byUsername==null) throw  new RuntimeException("you are not singup");
            return (UserDetails) byUsername;
        }
    }

